package com.cofbro.qian.signer

import android.content.Context
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URLEncoder

/**
 * 代签管理器 (CSF对齐版)
 *
 * 核心改进(对比旧版):
 * 1. 每用户独立HTTP客户端 + CookieJar (会话隔离)
 * 2. 自动重登: JSESSIONID过期时用AES加密密码重新登录
 * 3. 完整参数: 注入主用户prepareAll的结果(objectId/faceEnc/validate)
 * 4. 真实坐标: GPS → 缓存 → 预设地址 (修复errorLocation1)
 * 5. 位置重试: errorLocation时自动遍历预设地址
 * 6. 错误分类: 区分已签到/已过期/不在班级/会话废弃
 * 7. 会话废弃标记: Cookie过期且无法重登时标记isSessionObsolete
 */
class ProxySignManager(
    private val context: Context,
    private val aid: String,
    private val signType: String = "",
    private val enc: String? = null,
    private val fullIdParams: String? = null,
    /** 主用户prepareAll的结果, 代签时复用避免重复处理 */
    private val preparedParams: Map<String, String> = emptyMap(),
    /** 真实坐标(调用方传入, 或由ProxySignManager自行获取) */
    private var lat: String = "-1",
    private var lon: String = "-1",
    private var addr: String = ""
) {
    companion object {
        private const val TAG = "ProxySignManager"
    }

    /** 代签用户列表 */
    private val proxyUsers = mutableListOf<ProxyUserClient>()

    /** 回调: 进度更新 */
    var onProgress: (current: Int, total: Int, name: String, success: Boolean) -> Unit = { _, _, _, _ -> }

    /**
     * 代签结果摘要(含每用户详细结果)
     */
    data class ProxySignSummary(
        val successCount: Int,
        val failCount: Int,
        val totalCount: Int,
        val results: List<SignResult>,
        /** obsoleteUids: 需要修复的废弃账户 */
        val obsoleteUsers: List<String> = emptyList()
    )

    /**
     * 加载代签用户
     * 从存储中读取所有账号, 创建独立客户端
     */
    suspend fun loadUsers(includeSelfCookies: Boolean = false) {
        withContext(Dispatchers.IO) {
            proxyUsers.clear()

            val accountData = AccountManager.loadAllAccountData(context)
            val users = accountData.getJSONArrayExt(Constants.Account.USERS)
            val cookieSignData = AccountManager.loadAllAccountData(context, Constants.RecycleJson.COOKIE_JSON_DATA)
            val cookieUsers = cookieSignData.getJSONArrayExt(Constants.Account.USERS)

            // CSF对齐: excluded_uids=排除列表, 空=全部参与(默认)
            val prefs = context.getSharedPreferences("proxy_sign_selection", android.content.Context.MODE_PRIVATE)
            val excludedUids = prefs.getStringSet("excluded_uids", emptySet<String>()) ?: emptySet()

            // 合并两个来源的用户
            val allUsers = (0 until users.size).map { users.getJSONObject(it) }.toMutableList()
            for (cookieUser in (0 until cookieUsers.size).map { cookieUsers.getJSONObject(it) }) {
                val timestamp = cookieUser.getStringExt(Constants.Account.TIME)?.toLong() ?: 0L
                if (System.currentTimeMillis() - timestamp <= 24 * 60 * 60 * 1000) {
                    if (allUsers.none { it.getStringExt(Constants.Account.UID) == cookieUser.getStringExt(Constants.Account.UID) }) {
                        allUsers.add(cookieUser)
                    }
                }
            }

            // 创建每用户独立客户端
            for (user in allUsers) {
                val uName = user.getStringExt(Constants.Account.USERNAME) ?: ""
                val pwd = AccountManager.decryptPassword(user.getStringExt(Constants.Account.PASSWORD) ?: "")
                val uUid = user.getStringExt(Constants.Account.UID) ?: ""
                val uFid = user.getStringExt(Constants.Account.FID) ?: ""
                val cookies = user.getStringExt(Constants.Account.COOKIE) ?: ""

                if (cookies.isEmpty() && (uName.isEmpty() || pwd.isEmpty())) continue

                // 代签开关过滤: UID在排除列表 → 跳过
                if (uUid.isNotEmpty() && uUid in excludedUids) {
                    DebugLogCollector.d(TAG, "跳过(已关闭代签): $uUid")
                    continue
                }

                val remark = user.getStringExt(Constants.Account.REMARK) ?: ""
                // 先用masked手机号或remark作为初始名
                val initialName = if (remark.isNotEmpty()) remark
                    else if (uName.length == 11) "${uName.take(3)}****${uName.takeLast(2)}"
                    else uName

                val client = ProxyUserClient(
                    phone = uName,
                    password = pwd,
                    initialCookies = cookies,
                    uid = uUid,
                    fid = uFid,
                    name = initialName
                )

                // 尝试获取真实姓名 (CSF对齐: GET i.chaoxing.com/base)
                // 失败不影响代签, 仅用于美化显示
                try {
                    val realName = client.fetchRealName(context)
                    if (!realName.isNullOrEmpty()) {
                        client.name = realName
                    }
                } catch (_: Exception) {}
                // fetchRealName可能触发302导致误标记, 重置
                if (client.isSessionObsolete && cookies.isNotEmpty()) {
                    client.isSessionObsolete = false
                    client.lastError = ""
                }

                proxyUsers.add(client)
            }

            DebugLogCollector.d(TAG, "加载代签用户: ${proxyUsers.size}")
        }
    }

    /**
     * 为所有用户执行代签
     * 每用户独立流程: 预签到 → 构建完整URL → 执行 → 位置重试 → 错误分类
     */
    suspend fun signForAll(): ProxySignSummary = withContext(Dispatchers.IO) {
        if (proxyUsers.isEmpty()) {
            return@withContext ProxySignSummary(0, 0, 0, emptyList())
        }

        // 确保有真实坐标 (GPS → 缓存 → 预设)
        acquireLocationIfNeeded()

        val results = mutableListOf<SignResult>()
        var successCount = 0
        var failCount = 0
        val total = proxyUsers.size
        val obsoleteUsers = mutableListOf<String>()

        for ((index, user) in proxyUsers.withIndex()) {
            // 跳过已废弃的会话
            if (user.isSessionObsolete) {
                DebugLogCollector.d(TAG, "跳过废弃会话: ${user.name}")
                results.add(SignResult(false, "会话已过期, 请修复"))
                failCount++
                onProgress(index + 1, total, user.name, false)
                continue
            }

            user.state.isLoading = true
            onProgress(index, total, user.name, false)

            try {
                val result = signForUser(user)
                results.add(result)

                // CSF对齐: 已签到/非本班=跳过不计失败, 仅真正成功算成功
                val isEffectiveSuccess = result.isSuccess() || result.isAlreadySigned || result.isNotInClass
                if (isEffectiveSuccess) {
                    user.state.isSuccess = true
                    successCount++
                    onProgress(index + 1, total, user.name, true)
                } else {
                    user.state.isSuccess = false
                    user.state.error = result.message
                    failCount++
                    onProgress(index + 1, total, user.name, false)

                    // 收集废弃会话
                    if (user.isSessionObsolete) {
                        obsoleteUsers.add(user.phone)
                    }

                    // 如果是签到截止则停止后续代签
                    if (result.isEnded) {
                        DebugLogCollector.d(TAG, "签到已截止, 停止后续代签")
                        break
                    }
                }
            } catch (e: Exception) {
                user.state.isSuccess = false
                user.state.error = e.message ?: "未知异常"
                results.add(SignResult(false, e.message ?: "异常"))
                failCount++
                onProgress(index + 1, total, user.name, false)
            } finally {
                user.state.isLoading = false
            }

            delay(500) // 避免请求过快
        }

        DebugLogCollector.d(TAG, "代签完成: 成功$successCount 失败$failCount 共$total 废弃${obsoleteUsers.size}")
        ProxySignSummary(successCount, failCount, total, results, obsoleteUsers)
    }

    /**
     * 获取真实坐标: GPS → 缓存 → 预设地址
     */
    private suspend fun acquireLocationIfNeeded() {
        if (lat != "-1" && lon != "-1") return

        // 1. 尝试真机GPS
        var nativeLat = 0.0; var nativeLon = 0.0; var nativeAddr = ""
        try {
            NativeLocationUtils.getCurrentLocation(
                context,
                onSuccess = { l1, l2, a -> nativeLat = l1; nativeLon = l2; nativeAddr = a },
                onError = { DebugLogCollector.w(TAG, "定位失败: $it") }
            )
        } catch (_: Exception) {}
        if (nativeLat != 0.0 && nativeLon != 0.0) {
            lat = nativeLat.toString(); lon = nativeLon.toString(); addr = nativeAddr
            DebugLogCollector.d(TAG, "代签使用GPS: lat=$lat, lon=$lon")
            return
        }

        // 2. 尝试缓存
        CacheUtils.cache["default_Sign_latitude"]?.let { cacheLat ->
            CacheUtils.cache["default_Sign_longitude"]?.let { cacheLon ->
                lat = cacheLat; lon = cacheLon
                addr = CacheUtils.cache["default_Sign_Location"] ?: ""
                DebugLogCollector.d(TAG, "代签使用缓存: lat=$lat, lon=$lon")
                return
            }
        }

        // 3. 尝试预设地址
        val presets = PresetLocationManager.loadAll(context)
        if (presets.isNotEmpty()) {
            val loc = presets[0]
            val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
            lat = bdLatLon.latitude.toString(); lon = bdLatLon.longitude.toString()
            addr = loc.address.ifEmpty { loc.name }
            DebugLogCollector.d(TAG, "代签使用预设[0]: ${loc.name}, lat=$lat, lon=$lon")
        }
    }

    /**
     * 为指定用户签到 (CSF对齐版)
     *
     * 流程:
     * 1. 独立预签到 (analysis + analysis2, 使用用户自己的HTTP客户端)
     * 2. 构建完整签到URL (含所有预处理参数: objectId/faceEnc/validate/location JSON)
     * 3. 执行签到请求
     * 4. errorLocation → 遍历预设地址重试
     * 5. 错误分类 → SignResult
     */
    private suspend fun signForUser(user: ProxyUserClient): SignResult {
        // 1. 独立预签到 (使用用户自己的HTTP客户端)
        doPreSignForUser(user)
        delay(300)

        // 2. 构建完整签到URL
        val signUrl = buildCompleteSignUrl(user)

        // 3. 执行签到
        var body: String
        try {
            body = user.executeSign(aid, signUrl)
            DebugLogCollector.d(TAG, "${user.name} 签到响应: ${body.take(100)}")
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "${user.name} 签到请求异常", e)
            // 检查是否是认证失败
            if (e.message?.contains("passport") == true || e.message?.contains("login") == true) {
                user.markObsolete("认证失败: ${e.message}")
            }
            return SignResult(false, "${user.name}请求异常: ${e.message}")
        }

        // 4. 初步解析响应
        var result = SignResult.fromBody(body)

        // 5. errorLocation → 遍历预设地址重试 (CSF对齐)
        if (result.isLocationError) {
            DebugLogCollector.d(TAG, "${user.name} 位置不在范围, 遍历预设地址重试...")
            val presets = PresetLocationManager.loadAll(context)
            for ((i, loc) in presets.withIndex()) {
                val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                val retryAddr = loc.address.ifEmpty { loc.name }
                val retryUrl = buildCompleteSignUrl(
                    user,
                    overrideLat = bdLatLon.latitude.toString(),
                    overrideLon = bdLatLon.longitude.toString(),
                    overrideAddr = retryAddr
                )
                try {
                    body = user.executeSign(aid, retryUrl)
                    DebugLogCollector.d(TAG, "${user.name} 预设[${i}]签到: ${body.take(100)}")
                    result = SignResult.fromBody(body)
                    if (result.isSuccess()) break
                    if (result.isExpired || result.isEnded || result.isAlreadySigned) break
                    if (result.validateToken != null || result.faceToken != null) break
                } catch (e: Exception) {
                    DebugLogCollector.w(TAG, "${user.name} 预设[${i}]异常: ${e.message}")
                }
            }
        }

        // 6. 处理需要进一步验证的响应
        if (result.validateToken != null) {
            DebugLogCollector.d(TAG, "${user.name} 需要拍照验证, 尝试用预上传照片...")
            // 用预上传的objectId重试
            val objectId = preparedParams["objectId"]
            if (!objectId.isNullOrEmpty()) {
                val retryUrl = buildCompleteSignUrl(user) + "&objectId=$objectId"
                try {
                    body = user.executeSign(aid, retryUrl)
                    result = SignResult.fromBody(body)
                    if (result.isSuccess()) return result
                } catch (_: Exception) {}
            }
        }

        if (result.faceToken != null) {
            DebugLogCollector.d(TAG, "${user.name} 需要人脸识别, 尝试用预获取faceEnc...")
            val faceEnc = preparedParams["faceEnc"]
            val courseId = preparedParams["courseId"] ?: ""
            if (!faceEnc.isNullOrEmpty()) {
                val faceUrl = buildCompleteSignUrl(user) +
                        "&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}" +
                        "&ifCFP=0&courseId=$courseId&faceEnc=$faceEnc"
                try {
                    body = user.executeSign(aid, faceUrl)
                    result = SignResult.fromBody(body)
                    if (result.isSuccess()) return result
                } catch (_: Exception) {}
            }
        }

        // 7. 检测登录过期 → 尝试重登并重试
        if (body.contains("请先登录") || body.contains("请登录") ||
            body.contains("passport") || body.contains("重新登录")) {
            DebugLogCollector.d(TAG, "${user.name} 检测到登录过期, 尝试重登...")
            if (user.tryReLogin()) {
                // 重登成功 → 用新session重试签到
                DebugLogCollector.d(TAG, "${user.name} 重登成功, 重试签到")
                try {
                    val retryUrl = buildCompleteSignUrl(user)
                    body = user.executeSign(aid, retryUrl)
                    DebugLogCollector.d(TAG, "${user.name} 重登后签到: ${body.take(100)}")
                    result = SignResult.fromBody(body)
                    if (result.isSuccess()) return result
                } catch (e: Exception) {
                    DebugLogCollector.w(TAG, "${user.name} 重登后签到异常: ${e.message}")
                }
            }
            if (!result.isSuccess()) {
                user.markObsolete("登录过期, 重登${if (user.isSessionObsolete) "失败" else "后仍失败"}")
            }
        }

        return result
    }

    /**
     * 独立预签到 (使用用户自己的HTTP客户端)
     */
    private suspend fun doPreSignForUser(user: ProxyUserClient) {
        try {
            val analysisUrl = "https://mobilelearn.chaoxing.com/pptSign/analysis?DB_STRATEGY=RANDOM&aid=$aid&vs=1"
            val analysisReq = okhttp3.Request.Builder().url(analysisUrl).build()
            val analysisBody = user.execute(analysisReq).use { it.body?.string() ?: "" }
            val analysis2Code = analysisBody.substringAfter("code='+'").substringBefore("'")
            if (analysis2Code.isNotEmpty()) {
                user.execute(okhttp3.Request.Builder()
                    .url("https://mobilelearn.chaoxing.com/pptSign/analysis2?DB_STRATEGY=RANDOM&code=$analysis2Code")
                    .build()).close()
            }
            DebugLogCollector.d(TAG, "${user.name} 预签到完成")
        } catch (e: Exception) {
            DebugLogCollector.w(TAG, "${user.name} 预签到异常: ${e.message}")
        }
    }

    /**
     * 构建完整签到URL (CSF对齐版)
     *
     * 包含所有参数:
     * - 基础: activeId, uid, name, fid, clientip, appType, ifTiJiao
     * - 位置: latitude, longitude, address, location JSON
     * - 二维码: enc
     * - 拍照验证: objectId
     * - 人脸识别: faceEnc, currentFaceId, ifCFP, courseId
     * - 验证码: validate
     * - 设备指纹: deviceCode
     */
    private fun buildCompleteSignUrl(
        user: ProxyUserClient,
        overrideLat: String? = null,
        overrideLon: String? = null,
        overrideAddr: String? = null
    ): String {
        val useLat = overrideLat ?: lat
        val useLon = overrideLon ?: lon
        val useAddr = overrideAddr ?: addr

        val sb = StringBuilder(
            "https://mobilelearn.chaoxing.com/pptSign/stuSignajax?&clientip=&appType=15&ifTiJiao=1&vpProbability=-1&vpStrategy="
        )

        // 二维码enc
        if (!enc.isNullOrEmpty()) sb.append("&enc=$enc")

        // 位置坐标
        sb.append("&latitude=$useLat&longitude=$useLon")

        // 用户身份
        val encodedName = try { URLEncoder.encode(user.name, "UTF-8") } catch (e: Exception) { user.name }
        sb.append("&activeId=$aid&uid=${user.uid}&name=$encodedName&fid=${user.fid}")

        // 地址
        if (useAddr.isNotEmpty()) {
            val encodedAddr = try { URLEncoder.encode(useAddr, "UTF-8") } catch (e: Exception) { useAddr }
            sb.append("&address=$encodedAddr")
        }

        // location JSON (有些活动分开参数不够, 必须这个)
        val locJsonAddr = useAddr.replace("\"", "\\\"")
        val locationJson = """{"result":1,"latitude":$useLat,"longitude":$useLon,"address":"$locJsonAddr"}"""
        sb.append("&location=${try { URLEncoder.encode(locationJson, "UTF-8") } catch (e: Exception) { locationJson }}")

        // 设备指纹
        sb.append("&deviceCode=${NetworkUtils.generateDeviceCode()}")

        // === 预处理参数(从主用户prepareAll复用) ===

        // 拍照验证
        val objectId = preparedParams["objectId"]
        if (!objectId.isNullOrEmpty()) sb.append("&objectId=$objectId")

        // 人脸识别
        val faceEnc = preparedParams["faceEnc"]
        if (!faceEnc.isNullOrEmpty()) {
            val currentFaceId = CacheUtils.cache["faceObjectId"] ?: ""
            val courseId = preparedParams["courseId"] ?: ""
            sb.append("&currentFaceId=$currentFaceId&ifCFP=0&courseId=$courseId&faceEnc=$faceEnc")
        }

        // 验证码
        val validate = preparedParams["validate"]
        if (!validate.isNullOrEmpty()) sb.append("&validate=$validate")

        return sb.toString()
    }

    /**
     * 获取所有用户状态
     */
    fun getUserStates(): List<Pair<String, ProxyUserClient.ProxySignState>> {
        return proxyUsers.map { it.name to it.state }
    }

    /**
     * 获取所有用户(含废弃状态)
     */
    fun getUsersWithStatus(): List<Triple<String, ProxyUserClient.ProxySignState, Boolean>> {
        return proxyUsers.map { Triple(it.name, it.state, it.isSessionObsolete) }
    }

    /**
     * 用户数
     */
    fun getUserCount(): Int = proxyUsers.size

    /** 获取用户名列表(供选择对话框) */
    fun getUserNames(): List<String> = proxyUsers.map { it.name }

    /** 检查用户是否废弃 */
    fun isUserObsolete(index: Int): Boolean =
        index < proxyUsers.size && proxyUsers[index].isSessionObsolete

    /** 过滤用户: 只保留 selected[i]=true 的用户 */
    fun filterSelected(selected: BooleanArray) {
        val filtered = mutableListOf<ProxyUserClient>()
        for (i in proxyUsers.indices) {
            if (i < selected.size && selected[i]) {
                filtered.add(proxyUsers[i])
            }
        }
        proxyUsers.clear()
        proxyUsers.addAll(filtered)
        DebugLogCollector.d(TAG, "筛选后用户: ${proxyUsers.size}")
    }
}
