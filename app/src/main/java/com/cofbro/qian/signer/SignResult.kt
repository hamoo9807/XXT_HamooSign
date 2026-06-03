package com.cofbro.qian.signer

/**
 * 签到结果封装
 * 替代到处 body.contains("成功")
 */
data class SignResult(
    val success: Boolean,
    val message: String = "",
    val rawBody: String = "",

    /** validate_XXXXXX 需要滑块验证码 (CSF对齐: ifNeedVCode=1) */
    val captchaToken: String? = null,
    /** validate_XXXXXX 需要上传照片验证 (旧称, 保留兼容) */
    val validateToken: String? = null,
    /** checkFace_ 需要人脸识别 */
    val faceToken: String? = null,
    /** errorLocation 位置不在范围 */
    val isLocationError: Boolean = false,
    /** 已签到过了 */
    val isAlreadySigned: Boolean = false,
    /** 二维码已过期 */
    val isExpired: Boolean = false,
    /** 迟到/已截止 */
    val isEnded: Boolean = false,
    /** 需要验证码 (通用) */
    val needCaptcha: Boolean = false,
    /** 不在班级中 */
    val isNotInClass: Boolean = false
) {
    fun isSuccess(): Boolean = success && captchaToken == null && validateToken == null && faceToken == null

    companion object {
        /**
         * 从服务器响应解析签到结果 (CSF对齐)
         *
         * 响应模式:
         * - "success" / "成功" → 签到成功
         * - "validate_0XXXXXX" → 需要滑块验证码 (ifNeedVCode=1)
         * - "validate_<token>" (非数字开头) → 需要上传照片验证
         * - "checkFace_<token>" → 需要人脸识别
         */
        fun fromBody(body: String): SignResult = when {
            body.contains("成功") || body.contains("success") ||
                body.contains("签到成功") -> SignResult(true, "签到成功", body)
            // validate_ 开头: 区分验证码和拍照
            body.startsWith("validate_") -> {
                val token = body.removePrefix("validate_")
                // CSF判断: validate_后跟数字开头 → 验证码; 否则 → 拍照
                if (token.isNotEmpty() && token[0].isDigit()) {
                    SignResult(false, "需滑块验证码", body, captchaToken = token, needCaptcha = true)
                } else {
                    SignResult(false, "需拍照验证", body, validateToken = token)
                }
            }
            body.startsWith("checkFace_") -> SignResult(false, "需人脸识别", body, faceToken = body.removePrefix("checkFace_"))
            body.contains("已签到") -> SignResult(false, "已签到过了", body, isAlreadySigned = true)
            body.contains("重新扫描") -> SignResult(false, "二维码已过期", body, isExpired = true)
            body.contains("下次早点") || body.contains("已截止") ||
                body.startsWith("success2") || body.contains("结束") -> SignResult(false, "签到已截止", body, isEnded = true)
            body.contains("errorLocation") || body.startsWith("errorLocation") ->
                SignResult(false, "位置不在范围", body, isLocationError = true)
            body.startsWith("validate") -> SignResult(false, "需验证码", body, needCaptcha = true)
            body.contains("校验失败") || body.contains("未查询到活动数据") ->
                SignResult(false, "不在班级中", body, isNotInClass = true)
            else -> SignResult(false, body, body)
        }
    }
}
