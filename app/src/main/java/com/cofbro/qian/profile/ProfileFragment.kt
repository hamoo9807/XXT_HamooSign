package com.cofbro.qian.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.hjq.toast.ToastUtils
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cofbro.hymvvmutils.base.BaseFragment
import com.cofbro.hymvvmutils.base.getBySp
import com.cofbro.hymvvmutils.base.saveUsedSp
import com.cofbro.qian.account.manager.AccountManagerActivity
import com.cofbro.qian.data.URL
import com.cofbro.qian.databinding.FragmentProfileBinding
import com.cofbro.qian.im.IMMonitorService
import com.cofbro.qian.login.LoginActivity
import com.cofbro.qian.profile.advice.AdviceFragment
import com.cofbro.qian.profile.update.UpdateDetailActivity
import com.cofbro.qian.record.SignRecordActivity
import com.cofbro.qian.utils.AccountManager
import com.cofbro.qian.utils.AmapKeyStore
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.Downloader
import com.cofbro.qian.utils.GlideEngine
import com.cofbro.qian.utils.PresetLocationManager
import com.cofbro.qian.utils.dp2px
import com.cofbro.qian.utils.getStatusBarHeight
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils
import com.alibaba.fastjson.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {
    override fun onAllViewCreated(savedInstanceState: Bundle?) {
        initObserver()
        doNetWork()
        initView()
        initEvent()
    }

    private fun initObserver() {
    }

    private fun doNetWork() {
    }

    private fun initView() {
        adjustMarginOfView()
        adjustContainerHeight()
        profileMessageInfo()
        checkSignWithStatus()
        updatePresetLocationStatus()
        updatePresetPhotoStatus()
        updateAmapKeyStatus()
        checkIMMonitorStatus()
    }

    private fun adjustContainerHeight() {
        binding?.tvLogin?.post {
            val bottomHeight = CacheUtils.cache[Constants.Cache.BOTTOM_BAR_HEIGHT]?.toInt() ?: 0
            val layout = binding?.tvLogin?.layoutParams as? MarginLayoutParams
            layout?.bottomMargin = bottomHeight
            binding?.tvLogin?.layoutParams = layout
//            val layout = binding?.profileContainer?.layoutParams
//            layout?.height = (binding?.profileContainer?.height ?: 0) + bottomHeight * 3
//            binding?.profileContainer?.layoutParams = layout
        }
    }

    private fun checkSignWithStatus() {
        val switch = requireActivity().getBySp("signWith")?.toBoolean() ?: false
        binding?.signWithButton?.isChecked = switch
    }

    private fun checkIMMonitorStatus() {
        binding?.imMonitorSwitch?.isChecked = IMMonitorService.isEnabled(requireContext())
    }

    private fun adjustMarginOfView() {
        val statusBarHeight = getStatusBarHeight(requireContext())
        val layoutParams = binding?.csMyInfo?.layoutParams as? MarginLayoutParams
        layoutParams?.topMargin = statusBarHeight + dp2px(requireContext(), 5)
    }

    @SuppressLint("SetTextI18n")
    private fun profileMessageInfo() {
        viewModel.uid.let {
            val options = RequestOptions().transform(
                CenterCrop(),
                RoundedCorners(dp2px(requireContext(), 5))
            )
            Glide.with(this)
                .load(URL.getAvtarImgPath(it))
                .apply(options)
                .into(binding!!.ivProfileUserIcon)
        }

        // 先显示缓存名, 然后异步获取真实姓名
        binding?.tvProfileUsername?.text = CacheUtils.cache["realName"]
            ?: CacheUtils.cache[Constants.USER.USERNAME] ?: "-"

        binding?.tvProfileId?.text = "uid: ${CacheUtils.cache[Constants.USER.UID] ?: "-"}"

        // 异步获取真实姓名
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val cookies = CacheUtils.getCurrentCookies()
                if (cookies.isNotEmpty()) {
                    val name = fetchRealNameCSF(cookies)
                    if (!name.isNullOrEmpty()) {
                        CacheUtils.setRealName(name)
                        withContext(Dispatchers.Main) {
                            binding?.tvProfileUsername?.text = name
                        }
                    }
                }
            } catch (_: Exception) {}
        }
    }

    /** CSF对齐: POST加密设备信息到userLogin4Uname.do获取真实姓名 */
    private suspend fun fetchRealNameCSF(cookies: String): String? = withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
                .followRedirects(false).build()
            var body: String? = null
            // 方式1: POST加密设备信息 (CSF主方式)
            try {
                val encryptedInfo = com.cofbro.qian.utils.DeviceInfoHelper.buildEncryptedDeviceInfo(requireContext())
                val req = Request.Builder().url("https://sso.chaoxing.com/apis/login/userLogin4Uname.do")
                    .addHeader("cookie", cookies)
                    .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                    .post(okhttp3.FormBody.Builder().add("data", encryptedInfo).build()).build()
                body = client.newCall(req).execute().use { it.body?.string() ?: "" }
            } catch (_: Exception) {}
            // 方式2: GET fallback
            if (body.isNullOrEmpty()) {
                val req = Request.Builder().url("https://sso.chaoxing.com/apis/login/userLogin4Uname.do")
                    .addHeader("cookie", cookies)
                    .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                    .get().build()
                body = client.newCall(req).execute().use { it.body?.string() ?: "" }
            }
            JSONObject.parseObject(body ?: "").getJSONObject("msg")?.getString("name")
        } catch (_: Exception) { null }
    }


    private fun initEvent() {
        binding?.tvLogin?.setOnClickListener {
            viewModel.logoutDialog = LogoutDialog(requireContext()).apply {
                setCancelClickListener {
                    viewModel.logoutDialog?.dismiss()
                }
                setConfirmClickListener {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    clearUserInfo(context)
                    startActivity(intent)
                    requireActivity().finish()
                }
                setCancelable(false)
                show()
            }
        }

        binding?.bindAccounts?.setOnClickListener {
            val intent = Intent(requireActivity(), AccountManagerActivity::class.java)
            startActivity(intent)
        }

        binding?.signWithButton?.setOnClickListener {
            val switch = it as SwitchCompat
            requireActivity().saveUsedSp("signWith", switch.isChecked.toString())
        }

        binding?.signRecords?.setOnClickListener {
            val intent = Intent(requireActivity(), SignRecordActivity::class.java)
            startActivity(intent)
        }

        binding?.exportCookie?.setOnClickListener {
            exportCookie()
        }

        binding?.importCookie?.setOnClickListener {
            importCookie()
        }

        binding?.amapKeySetting?.setOnClickListener {
            showAmapKeySettingDialog()
        }

        binding?.groupSign?.setOnClickListener {
            val intent = Intent(requireContext(), com.cofbro.qian.im.GroupListActivity::class.java)
            startActivity(intent)
        }

        binding?.signFilter?.setOnClickListener {
            val states = com.cofbro.qian.utils.SignFilterManager.getAllFilterStates()
            val keys = com.cofbro.qian.utils.SignFilterManager.SIGN_TYPE_LABELS.keys.toList()
            val labels = com.cofbro.qian.utils.SignFilterManager.SIGN_TYPE_LABELS
            val items = keys.map { "${if (states[it] == true) "[x]" else "[ ]"} ${labels[it] ?: it}" }.toTypedArray()
            val checked = keys.map { states[it] ?: true }.toMutableList()
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("签到类型筛选")
                .setMultiChoiceItems(items, checked.toBooleanArray()) { _, i, c -> checked[i] = c }
                .setPositiveButton("保存") { _, _ -> keys.forEachIndexed { i, k -> com.cofbro.qian.utils.SignFilterManager.setAutoSignEnabled(k, checked[i]) }; ToastUtils.show("已保存") }
                .setNeutralButton("全部开启") { _, _ -> com.cofbro.qian.utils.SignFilterManager.resetAll(); ToastUtils.show("已全部开启") }
                .setNegativeButton("取消", null).show()
        }

        binding?.presetLocation?.setOnClickListener {
            showPresetLocationDialog()
        }

        binding?.imMonitorSwitch?.setOnClickListener {
            val switch = it as SwitchCompat
            val cookies = CacheUtils.getCurrentCookies()
            if (switch.isChecked && cookies.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "请先登录后再开启IM监听", Toast.LENGTH_SHORT).show()
                switch.isChecked = false
                return@setOnClickListener
            }
            IMMonitorService.setEnabled(requireContext(), switch.isChecked)
            if (switch.isChecked) {
                Toast.makeText(requireContext(), "IM监听已开启", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "IM监听已关闭", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.presetPhoto?.setOnClickListener {
            selectPresetPhoto()
        }

        binding?.helpGuide?.setOnClickListener {
            showHelpGuideDialog()
        }
    }

    private fun selectPresetPhoto() {
        PictureSelector
            .create(this)
            .openGallery(SelectMimeType.ofImage())
            .setSandboxFileEngine { context, srcPath, mineType, call ->
                if (call != null) {
                    val sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
                    call.onCallback(srcPath, sandboxPath)
                }
            }
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    result?.get(0)?.let { media ->
                        val path = media.availablePath
                        copyToPresetPhoto(path)
                    }
                }

                override fun onCancel() {
                    Toast.makeText(requireContext(), "已取消", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun copyToPresetPhoto(sourcePath: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val dir = java.io.File(requireContext().getExternalFilesDir(null), "preset_photo")
                if (!dir.exists()) dir.mkdirs()

                // 删除旧照片
                dir.listFiles()?.forEach { it.delete() }

                // 复制新照片
                val ext = sourcePath.substringAfterLast(".").lowercase()
                val extName = if (ext in listOf("jpg", "jpeg", "png")) ext else "jpg"
                val targetFile = File(dir, "preset.${extName}")
                val sourceFile = File(sourcePath)
                if (sourceFile.exists()) {
                    sourceFile.copyTo(targetFile, overwrite = true)
                } else {
                    // content:// URI的情况
                    try {
                        val inputStream = requireContext().contentResolver.openInputStream(Uri.parse(sourcePath))
                        inputStream?.use { input ->
                            FileOutputStream(targetFile).use { output ->
                                input.copyTo(output)
                            }
                        }
                    } catch (e: Exception) {
                        throw Exception("无法读取照片: ${e.message}")
                    }
                }

                launch(Dispatchers.Main) {
                    updatePresetPhotoStatus()
                    Toast.makeText(requireContext(), "预设照片已设置: ${targetFile.name}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "设置预设照片失败: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updatePresetPhotoStatus() {
        val photoDir = java.io.File(requireContext().getExternalFilesDir(null), "preset_photo")
        val photoFile = if (photoDir.exists() && photoDir.isDirectory) {
            photoDir.listFiles()?.firstOrNull { it.extension.lowercase() in listOf("jpg", "jpeg", "png") }
        } else null

        if (photoFile != null && photoFile.exists()) {
            binding?.tvPresetPhotoStatus?.text = "${photoFile.name} (${photoFile.length() / 1024}KB)"
            binding?.tvPresetPhotoStatus?.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
        } else {
            binding?.tvPresetPhotoStatus?.text = "未设置"
            binding?.tvPresetPhotoStatus?.setTextColor(resources.getColor(android.R.color.darker_gray, null))
        }
    }

    private fun updateAmapKeyStatus() {
        val isUserKey = AmapKeyStore.isUsingUserKey()
        binding?.tvAmapKeyStatus?.text = if (isUserKey) "已配置" else "未配置"
        binding?.tvAmapKeyStatus?.setTextColor(resources.getColor(
            if (isUserKey) android.R.color.holo_green_dark else android.R.color.darker_gray, null))
    }

    private fun showAmapKeySettingDialog() {
        val currentKey = AmapKeyStore.getApiKey()
        val isUserKey = AmapKeyStore.isUsingUserKey()

        val editText = EditText(requireContext()).apply {
            hint = "输入高德地图API Key"
            setText(if (isUserKey) currentKey else "")
            setPadding(48, 24, 48, 24)
            setTextSize(13f)
            setSingleLine()
        }

        val scrollView = android.widget.ScrollView(requireContext()).apply {
            addView(editText)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("高德地图API Key 设置")
            .setMessage("开源后内置Key可能失效。\n请前往 lbs.amap.com 注册账号获取Key。\n当前Key: ${currentKey.take(8)}****${currentKey.takeLast(4)}")
            .setView(scrollView)
            .setPositiveButton("保存") { _, _ ->
                val key = editText.text.toString().trim()
                if (key.isNotEmpty()) {
                    AmapKeyStore.setApiKey(requireContext(), key)
                    updateAmapKeyStatus()
                    Toast.makeText(requireContext(), "API Key已保存", Toast.LENGTH_SHORT).show()
                } else {
                    AmapKeyStore.clearApiKey(requireContext())
                    updateAmapKeyStatus()
                    Toast.makeText(requireContext(), "已恢复默认Key", Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("清除") { _, _ ->
                AmapKeyStore.clearApiKey(requireContext())
                updateAmapKeyStatus()
                Toast.makeText(requireContext(), "已清除自定义Key", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showHelpGuideDialog() {
        val helpText = """
            📚 学习通签到助手 - 使用攻略

            ⚙️ 首次使用配置
            ▸ 高德地图API Key
              开源版内置Key可能失效。
              前往 lbs.amap.com 注册 → 应用管理 → 创建应用
              → 添加Key(Android平台, SHA1随意) → 复制Key
              在"个人中心→高德Key"中粘贴保存。
            ▸ 预设照片
              拍照验证签到需要。个人中心→预设照片→从相册选择。
            ▸ 预设位置
              定位签到需要。个人中心→预留地址→添加常用位置。
            ▸ 登录账号
              超星学习通账号密码登录。

            🔹 扫码签到（推荐）
            首页右上角扫码 → 扫描老师展示的二维码 → 自动完成。
            扫码即签, 无需进课程找活动。
            支持: 普通/二维码/定位/手势/签到码

            🔹 防作弊扫一扫
            首页"防作弊"按钮, 扫码后有4种模式:
            • 智能签到(推荐): 自动检测验证码/人脸/照片, 预处理后一次性签
            • 验证码签到: 滑块验证码 / 文字输入
            • 人脸识别绕过: 4种方式
            • 照片验证签到: 上传预设照片

            🔹 智能预检流程
            签到前先查 ifNeedVCode / openCheckFaceFlag / ifPhoto
            → 处理验证码 → 处理人脸 → 上传照片 → 一次性签到
            所有参数准备好再签, 不浪费enc。

            🔹 查签到
            首页"查签到" → 自动扫描所有课程进行中的签到 → 点击签到。

            🔹 群聊签到
            个人中心"群聊签到" → 查看IM群聊中的签到活动。

            🔹 定位签到
            个人中心→预留地址 设置常用位置。
            签到自动尝试当前位置, 失败后遍历预留地址。

            🔹 手势/签到码暴力破解
            手势: 4-6位不重复数字排列(约8万种)
            签到码: 4-6位数字组合

            🔹 代签功能
            个人中心开启"代签所有绑定账号"。
            签到成功后自动为绑定账号签到。
            代签账号建议同时保存手机号+密码(支持自动重登)。

            🔹 IM监听签到
            个人中心开启"IM监听", 后台监听签到通知自动签到。

            🔹 预设照片
            个人中心→预设照片→从相册选择。
            用于拍照验证和人脸识别。

            🔹 手动签到(Debug)
            首页"LOG" → "🔧 手动签到"。
            自动失败时可手动填参数补签。

            🔹 注意事项
            • 定位签到需要开启GPS
            • 二维码签到enc一次性, 失败需重新扫码
            • 代签需提前绑定账号
            • 日志包含详细签到过程, 失败时可查看
        """.trimIndent()

        val scrollView = android.widget.ScrollView(requireContext())
        val textView = android.widget.TextView(requireContext()).apply {
            text = helpText
            textSize = 13f
            setPadding(24, 16, 24, 16)
            setLineSpacing(8f, 1.0f)
            setTextIsSelectable(true)
        }
        scrollView.addView(textView)
        AlertDialog.Builder(requireContext())
            .setTitle("📖 使用攻略")
            .setView(scrollView)
            .setPositiveButton("知道了", null)
            .show()
    }

    private fun exportCookie() {
        val cookies = CacheUtils.getCurrentCookies()
        if (cookies.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "当前没有Cookie可导出", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            // CSF对齐: 获取真实姓名
            try { fetchRealNameCSF(cookies)?.let { CacheUtils.setRealName(it) } } catch (_: Exception) {}
            val json = CacheUtils.getExportCookieJson()
            withContext(Dispatchers.Main) {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("cookie", json))
                AlertDialog.Builder(requireContext())
                    .setTitle("Cookie已导出")
                    .setMessage("Cookie已复制到剪贴板，可发送给他人导入实现代签。")
                    .setPositiveButton("确定", null)
                    .show()
            }
        }
    }

    private fun importCookie() {
        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 16, 48, 0)
        }
        val etPhone = EditText(requireContext()).apply {
            hint = "手机号(可选,用于自动重登)"
            setSingleLine()
            textSize = 13f
        }
        val etPassword = EditText(requireContext()).apply {
            hint = "密码(可选,用于自动重登)"
            setSingleLine()
            textSize = 13f
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        val etCookie = EditText(requireContext()).apply {
            hint = "粘贴Cookie JSON"
            setPadding(0, 12, 0, 0)
            setTextSize(12f)
            minLines = 3
            maxLines = 6
        }
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        if (clip != null && clip.itemCount > 0) {
            val text = clip.getItemAt(0)?.text?.toString() ?: ""
            if (text.contains("\"cookies\"")) {
                etCookie.setText(text)
            }
        }
        layout.addView(etPhone)
        layout.addView(etPassword)
        layout.addView(etCookie)

        AlertDialog.Builder(requireContext())
            .setTitle("导入账号(代签)")
            .setView(layout)
            .setPositiveButton("导入") { _, _ ->
                val phone = etPhone.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val input = etCookie.text.toString().trim()
                if (input.isEmpty() && phone.isEmpty()) {
                    Toast.makeText(requireContext(), "请输入Cookie或手机号", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                try {
                    var cookies = ""
                    var uid = ""
                    var fid = ""
                    var username = "代签用户"

                    if (input.isNotEmpty()) {
                        val json = com.alibaba.fastjson.JSONObject.parseObject(input)
                        cookies = json.getString("cookies") ?: ""
                        uid = json.getString("uid") ?: ""
                        fid = json.getString("fid") ?: ""
                        // 优先用真实姓名 > username
                        username = json.getString("name")?.takeIf { it.isNotEmpty() }
                            ?: json.getString("username") ?: username
                        if (phone.isEmpty() && json.containsKey("phone")) {
                            etPhone.setText(json.getString("phone"))
                        }
                        if (password.isEmpty() && json.containsKey("password")) {
                            etPassword.setText(json.getString("password"))
                        }
                    }

                    val finalPhone = etPhone.text.toString().trim()
                    val finalPassword = etPassword.text.toString().trim()

                    if (cookies.isEmpty() && uid.isEmpty()) {
                        Toast.makeText(requireContext(), "缺少Cookie或UID", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    saveImportedAccount(cookies, uid, fid, username, finalPhone, finalPassword)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "解析失败: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun saveImportedAccount(cookies: String, uid: String, fid: String, username: String, phone: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val accountData = AccountManager.loadAllAccountData(requireContext())
            val account = com.alibaba.fastjson.JSONObject().apply {
                put(Constants.Account.USERNAME, if (phone.isNotEmpty()) phone else username)
                put(Constants.Account.PASSWORD, AccountManager.encryptPassword(password))
                put(Constants.Account.UID, uid)
                put(Constants.Account.COOKIE, cookies)
                put(Constants.Account.FID, fid)
                put(Constants.Account.PIC_URL, URL.getAvtarImgPath(uid))
                put(Constants.Account.REMARK, username)
            }
            val updatedData = AccountManager.bindAccounts(requireContext(), accountData, account)
            if (updatedData != null) {
                AccountManager.updateAccountData(requireContext(), updatedData.toJSONString())
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "账号导入成功！支持自动重登", Toast.LENGTH_LONG).show()
                }
            } else {
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "导入失败（账号已存在？）", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updatePresetLocationStatus() {
        val presets = PresetLocationManager.loadAll(requireContext())
        if (presets.isNotEmpty()) {
            binding?.tvPresetLocationStatus?.text = "${presets.size}个地址"
            binding?.tvPresetLocationStatus?.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
        } else {
            binding?.tvPresetLocationStatus?.text = "未设置"
            binding?.tvPresetLocationStatus?.setTextColor(resources.getColor(android.R.color.darker_gray, null))
        }
    }

    private fun showPresetLocationDialog() {
        val presets = PresetLocationManager.loadAll(requireContext())
        val items = mutableListOf<String>()
        for ((i, loc) in presets.withIndex()) {
            items.add("${i + 1}. ${loc.name.ifEmpty { loc.address.ifEmpty { "地址" + (i+1) } }} (${loc.latitude}, ${loc.longitude})")
        }
        items.add("+ 添加新地址")

        AlertDialog.Builder(requireContext())
            .setTitle("预留地址 (${presets.size}个)")
            .setItems(items.toTypedArray()) { _, which ->
                if (which == presets.size) {
                    showAddPresetLocationDialog()
                } else {
                    showPresetLocationDetailDialog(which, presets[which])
                }
            }
            .setNegativeButton("关闭", null)
            .show()
    }

    private fun showAddPresetLocationDialog() {
        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
        }
        val etName = EditText(requireContext()).apply {
            hint = "名称, 如: 教学楼A"
            setSingleLine()
            textSize = 13f
        }
        val etLat = EditText(requireContext()).apply {
            hint = "纬度(latitude), 如: 21.192620"
            setSingleLine()
            textSize = 13f
        }
        val etLon = EditText(requireContext()).apply {
            hint = "经度(longitude), 如: 110.257421"
            setSingleLine()
            textSize = 13f
        }
        val etAddr = EditText(requireContext()).apply {
            hint = "详细地址(可选), 如: 广东海洋大学"
            setSingleLine()
            textSize = 13f
        }
        layout.addView(etName)
        layout.addView(etLat)
        layout.addView(etLon)
        layout.addView(etAddr)

        // 添加"查看坐标"提示链接
        val tvCoordHint = android.widget.TextView(requireContext()).apply {
            text = "点击查看坐标: lbs.baidu.com/maptool/getpoint"
            setTextColor(android.graphics.Color.parseColor("#1E88E5"))
            textSize = 12f
            setPadding(0, 12, 0, 0)
            setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://lbs.baidu.com/maptool/getpoint"))
                startActivity(intent)
            }
        }
        layout.addView(tvCoordHint)

        AlertDialog.Builder(requireContext())
            .setTitle("添加预留地址")
            .setView(layout)
            .setPositiveButton("添加") { _, _ ->
                val name = etName.text.toString().trim()
                val lat = etLat.text.toString().trim()
                val lon = etLon.text.toString().trim()
                val addr = etAddr.text.toString().trim()
                if (lat.isEmpty() || lon.isEmpty()) {
                    Toast.makeText(requireContext(), "经纬度不能为空", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                try {
                    PresetLocationManager.add(requireContext(), PresetLocationManager.PresetLocation(
                        name = name, address = addr, latitude = lat.toDouble(), longitude = lon.toDouble()
                    ))
                    updatePresetLocationStatus()
                    Toast.makeText(requireContext(), "已添加: ${name.ifEmpty { addr }}", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "经纬度格式错误", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showPresetLocationDetailDialog(index: Int, loc: PresetLocationManager.PresetLocation) {
        AlertDialog.Builder(requireContext())
            .setTitle(loc.name.ifEmpty { loc.address.ifEmpty { "地址" + (index + 1) } })
            .setMessage("纬度: ${loc.latitude}\n经度: ${loc.longitude}\n地址: ${loc.address}")
            .setPositiveButton("编辑") { _, _ ->
                showEditPresetLocationDialog(index, loc)
            }
            .setNeutralButton("删除") { _, _ ->
                PresetLocationManager.remove(requireContext(), index)
                updatePresetLocationStatus()
                Toast.makeText(requireContext(), "已删除", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("关闭", null)
            .show()
    }

    private fun showEditPresetLocationDialog(index: Int, loc: PresetLocationManager.PresetLocation) {
        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
        }
        val etName = EditText(requireContext()).apply {
            hint = "名称"
            setText(loc.name)
            setSingleLine()
            textSize = 13f
        }
        val etLat = EditText(requireContext()).apply {
            hint = "纬度"
            setText(loc.latitude.toString())
            setSingleLine()
            textSize = 13f
        }
        val etLon = EditText(requireContext()).apply {
            hint = "经度"
            setText(loc.longitude.toString())
            setSingleLine()
            textSize = 13f
        }
        val etAddr = EditText(requireContext()).apply {
            hint = "详细地址"
            setText(loc.address)
            setSingleLine()
            textSize = 13f
        }
        layout.addView(etName)
        layout.addView(etLat)
        layout.addView(etLon)
        layout.addView(etAddr)

        AlertDialog.Builder(requireContext())
            .setTitle("编辑预留地址")
            .setView(layout)
            .setPositiveButton("保存") { _, _ ->
                val name = etName.text.toString().trim()
                val lat = etLat.text.toString().trim()
                val lon = etLon.text.toString().trim()
                val addr = etAddr.text.toString().trim()
                try {
                    val presets = PresetLocationManager.loadAll(requireContext()).toMutableList()
                    presets[index] = PresetLocationManager.PresetLocation(
                        name = name, address = addr, latitude = lat.toDouble(), longitude = lon.toDouble()
                    )
                    PresetLocationManager.saveAll(requireContext(), presets)
                    updatePresetLocationStatus()
                    Toast.makeText(requireContext(), "已保存", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "经纬度格式错误", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun clearUserInfo(context: Context) {
        context.saveUsedSp("username", "")
        context.saveUsedSp("password", "")
        CacheUtils.clearPersistedCookies()
        deleteHomeJSON()
        Toast.makeText(context, "数据删除成功", Toast.LENGTH_SHORT).show()
    }


    private fun deleteHomeJSON() {
        lifecycleScope.launch(Dispatchers.IO) {
            Downloader.delete(requireContext(), Constants.RecycleJson.HOME_JSON_DATA)
        }
    }
}