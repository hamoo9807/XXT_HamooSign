package com.cofbro.qian.im

import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.data.URL
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.safeParseToJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody

data class IMGroupInfo(
    val chatId: String,
    val chatName: String,
    val isGroup: Boolean,
    val picArray: List<String>
) : java.io.Serializable

data class IMSignActivity(
    val activeId: Long,
    val title: String,
    val courseName: String,
    val activeTypeName: String,
    val classId: Int,
    val courseId: Int,
    val startTimeTitle: String
)

/**
 * IM群聊签到活动查询
 * 从学习通IM中获取群列表和历史消息中的签到活动
 */
object IMGroupHelper {
    private const val TAG = "IMGroupHelper"
    private const val URL_IM_GROUPS = "https://im.chaoxing.com/webim/message/list/getMessageList"
    private const val URL_MESSAGE_ROAMING = "https://a1-vip6.easecdn.com/cx-dev/cxstudy/users/%s/messageroaming"

    /**
     * 获取IM配置(myName, myToken, myTuid, myPuid, myFid)
     */
    suspend fun getIMConfig(): JSONObject? = withContext(Dispatchers.IO) {
        try {
            val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.IM_ME_URL))
            val html = resp.data?.body?.string() ?: return@withContext null
            val doc = org.jsoup.Jsoup.parse(html)
            val config = JSONObject()
            config["myName"] = doc.select("#myName").text()
            config["myToken"] = doc.select("#myToken").text()
            config["myTuid"] = doc.select("#myTuid").text()
            config["myPuid"] = doc.select("#myPuid").text()
            config["myFid"] = doc.select("#myFid").text()
            if (config.getString("myToken").isNullOrEmpty()) return@withContext null
            return@withContext config
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取IM配置异常", e)
            null
        }
    }

    /**
     * 获取IM群列表
     */
    suspend fun getIMGroups(config: JSONObject): List<IMGroupInfo> = withContext(Dispatchers.IO) {
        try {
            val cookies = CacheUtils.getCurrentCookies()
            val formBody = FormBody.Builder()
                .add("tuid", config.getString("myTuid") ?: "")
                .add("puid", config.getString("myPuid") ?: "")
                .add("token", config.getString("myToken") ?: "")
                .build()
            val request = Request.Builder()
                .url(URL_IM_GROUPS)
                .addHeader("cookie", cookies)
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("User-Agent",
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .post(formBody)
                .build()
            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: ""
            val json = body.safeParseToJson() ?: return@withContext emptyList()
            val data = json.getJSONArray("data") ?: return@withContext emptyList()
            val groups = mutableListOf<IMGroupInfo>()
            for (i in 0 until data.size) {
                val item = data.getJSONObject(i)
                val pics = mutableListOf<String>().apply {
                    item.getJSONArray("picArray")?.forEach { add(it.toString()) }
                    if (isEmpty()) add(item.getString("chatIco")
                        ?: "https://im.chaoxing.com/res/images/course_logo.png")
                }
                groups.add(IMGroupInfo(
                    chatId = item.getString("chatId") ?: continue,
                    chatName = item.getString("chatName") ?: "无群聊名称",
                    isGroup = item.getIntValue("isGroup") == 0,
                    picArray = pics
                ))
            }
            DebugLogCollector.d(TAG, "获取群列表: ${groups.size}个群")
            return@withContext groups
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取群列表异常", e)
            emptyList()
        }
    }

    /**
     * 获取群聊历史消息中的签到活动
     */
    suspend fun getIMSignActivities(
        config: JSONObject, group: IMGroupInfo
    ): List<IMSignActivity> = withContext(Dispatchers.IO) {
        try {
            val queue = if (group.isGroup) "${group.chatId}@conference.easemob.com" else "${group.chatId}@easemob.com"
            val bodyJson = JSONObject().apply {
                put("end", "-1")
                put("queue", queue)
                put("start", "-1")
            }
            val request = Request.Builder()
                .url(URL_MESSAGE_ROAMING.format(config.getString("myTuid")))
                .addHeader("Authorization", "Bearer ${config.getString("myToken")}")
                .addHeader("Content-Type", "text/plain;charset=UTF-8")
                .post(RequestBody.create("text/plain;charset=UTF-8".toMediaTypeOrNull(), bodyJson.toJSONString()))
                .build()
            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: ""
            val json = body.safeParseToJson() ?: return@withContext emptyList()
            val data = json.getJSONObject("data") ?: return@withContext emptyList()
            val msgs = data.getJSONArray("msgs") ?: return@withContext emptyList()
            val activities = mutableListOf<IMSignActivity>()

            for (i in 0 until msgs.size) {
                try {
                    val msgObj = msgs.getJSONObject(i)
                    val msgStr = msgObj.getString("msg") ?: continue
                    val msgBytes = android.util.Base64.decode(msgStr, android.util.Base64.DEFAULT)
                    val metaBytes = extractProtobufField6(msgBytes) ?: continue
                    val metaJson = JSONObject.parseObject(String(metaBytes, Charsets.UTF_8))
                    val extList = metaJson.getJSONArray("extList") ?: continue
                    for (j in 0 until extList.size) {
                        val ext = extList.getJSONObject(j)
                        if (ext.getString("key") == "attachment") {
                            val attach = JSONObject.parseObject(ext.getString("stringValue") ?: continue)
                            if (attach.getIntValue("attachmentType") != 15) continue
                            val signInfo = attach.getJSONObject("att_chat_course") ?: continue
                            val courseInfo = signInfo.getJSONObject("courseInfo") ?: continue
                            val activeId = signInfo.getLong("aid") ?: continue
                            val atype = signInfo.getIntValue("atype")
                            if (atype != 2 && atype != 74) continue
                            activities.add(IMSignActivity(
                                activeId = activeId,
                                title = signInfo.getString("title") ?: "签到",
                                courseName = courseInfo.getString("coursename") ?: "",
                                activeTypeName = signInfo.getString("atypeName") ?: "签到",
                                classId = courseInfo.getIntValue("classid"),
                                courseId = courseInfo.getString("courseid").toIntOrNull() ?: 0,
                                startTimeTitle = signInfo.getString("subTitle") ?: ""
                            ))
                        }
                    }
                } catch (_: Exception) { continue }
            }
            DebugLogCollector.d(TAG, "获取签到活动: ${activities.size}个")
            return@withContext activities
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取签到活动异常", e)
            emptyList()
        }
    }

    /**
     * Protobuf解析: 提取field6 (tag=0x32) 的内容
     * 环信消息体中的field6是一个JSON字符串(extList)
     * 正确实现: 处理varint编码的length, 跳过其他字段
     */
    private fun extractProtobufField6(data: ByteArray): ByteArray? {
        return try {
            var i = 0
            while (i < data.size) {
                // 读取varint编码的tag (field_number << 3 | wire_type)
                val tag = readVarint(data, i) ?: break
                i += varintSize(data, i)
                val fieldNum = tag shr 3
                val wireType = tag and 0x07

                if (fieldNum == 6L && wireType == 2L) {
                    // field6, length-delimited → 这就是我们要的
                    val len = readVarint(data, i) ?: break
                    i += varintSize(data, i)
                    if (i + len.toInt() <= data.size) return data.copyOfRange(i, i + len.toInt())
                    return null
                }

                // 跳过其他字段
                when (wireType.toInt()) {
                    0 -> { // varint
                        readVarint(data, i) ?: break
                        i += varintSize(data, i)
                    }
                    1 -> i += 8 // 64-bit fixed
                    2 -> { // length-delimited
                        val len = readVarint(data, i) ?: break
                        i += varintSize(data, i) + len.toInt()
                    }
                    5 -> i += 4 // 32-bit fixed
                    else -> break
                }
            }
            null
        } catch (e: Exception) { null }
    }

    /** 读取varint */
    private fun readVarint(data: ByteArray, offset: Int): Long? {
        if (offset >= data.size) return null
        var result = 0L
        var shift = 0
        var pos = offset
        while (pos < data.size) {
            val byte = data[pos].toInt() and 0xFF
            result = result or ((byte.toLong() and 0x7F) shl shift)
            shift += 7
            pos++
            if (byte and 0x80 == 0) return result
            if (shift >= 64) return null
        }
        return null
    }

    /** varint编码占用的字节数 */
    private fun varintSize(data: ByteArray, offset: Int): Int {
        var pos = offset
        while (pos < data.size && (data[pos].toInt() and 0xFF) >= 0x80) pos++
        return pos - offset + 1
    }
}
