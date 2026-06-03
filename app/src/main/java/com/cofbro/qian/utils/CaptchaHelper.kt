package com.cofbro.qian.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.alibaba.fastjson.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.security.MessageDigest
import java.util.UUID

/**
 * 滑块验证码处理
 * 对接 captcha.chaoxing.com 的行为验证码(滑块拼图)
 * 流程: getConf → getCaptchaImage (shade+cutout) → 用户滑动 → checkResult → validate
 */
object CaptchaHelper {
    private const val TAG = "CaptchaHelper"
    private const val CAPTCHA_ID = "Qt9FIw9o4pwRjOyqM6yizZBh682qN2TU"
    private const val URL_CAPTCHA_CONF = "https://captcha.chaoxing.com/captcha/get/conf"
    private const val URL_CAPTCHA_IMAGE = "https://captcha.chaoxing.com/captcha/get/verification/image"
    private const val URL_CAPTCHA_RESULT = "https://captcha.chaoxing.com/captcha/check/verification/result"

    data class CaptchaData(
        val captchaId: String,
        val type: String,
        val version: String,
        val token: String,
        val captchaKey: String,
        val iv: String,
        /** 滑块背景图(base64) **/
        val shadeImage: String,
        /** 滑块小块图(base64) **/
        val cutoutImage: String
    )

    /**
     * 获取验证码服务器时间戳
     */
    private suspend fun getCaptchaConf(): Long? = withContext(Dispatchers.IO) {
        try {
            // CSF对齐: callback=cx_captcha_function 是JSONP必须参数
            val url = "$URL_CAPTCHA_CONF?callback=cx_captcha_function&captchaId=$CAPTCHA_ID&_=${System.currentTimeMillis()}"
            val request = Request.Builder().url(url)
                .addHeader("Referer", "https://mobilelearn.chaoxing.com/")
                .addHeader("cookie", CacheUtils.getCurrentCookies())
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .build()
            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: return@withContext null
            DebugLogCollector.d(TAG, "getCaptchaConf raw: ${body.take(300)}")
            val cleanedBody = body
                .replace("cx_captcha_function(", "")
                .replace(")", "")
                .trim()
            val json = JSONObject.parseObject(cleanedBody)
            val t = json.getLong("t")
            DebugLogCollector.d(TAG, "getCaptchaConf t=$t")
            return@withContext if (t > 0) t else null
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取验证码配置异常: ${e.message}, body头200字见上")
            null
        }
    }

    /**
     * 获取滑块验证码图片数据 (shadeImage=背景图, cutoutImage=滑块)
     * 返回 CaptchaData 包含base64图片和验证所需的token/key/iv
     */
    /**
     * 获取滑块验证码图片数据 (CSF对齐: getCaptchaImageV2)
     *
     * 关键区别: 请求中必须带 referer 查询参数指向 preSign URL,
     * 服务器用此验证请求是否来自合法的签到页面, 否则返回 {"10030":"r verify error"}
     */
    suspend fun getCaptchaImage(
        courseId: String = "",
        classId: String = "",
        activeId: String = "",
        uid: String = ""
    ): CaptchaData? = withContext(Dispatchers.IO) {
        try {
            val t = getCaptchaConf() ?: return@withContext null
            val type = "slide"
            val version = "1.1.20"
            val captchaKey = md5("$t${UUID.randomUUID()}")
            val iv = md5("$CAPTCHA_ID$type${System.currentTimeMillis()}${UUID.randomUUID()}")
            val token = md5("$t$CAPTCHA_ID$type$captchaKey") + ":${t + 300000L}"

            // CSF对齐: 构建preSign URL作为referer参数
            val refererUrl = "https://mobilelearn.chaoxing.com/newsign/preSign" +
                    "?courseId=$courseId&classId=$classId" +
                    "&activePrimaryId=$activeId&uid=$uid" +
                    "&general=1&sys=1&ls=1&appType=15&isTeacherViewOpen=0"

            val url = "$URL_CAPTCHA_IMAGE?callback=cx_captcha_function" +
                    "&captchaId=$CAPTCHA_ID&type=$type&version=$version" +
                    "&captchaKey=$captchaKey&token=$token&iv=$iv" +
                    "&referer=${java.net.URLEncoder.encode(refererUrl, "UTF-8")}" +
                    "&_=${System.currentTimeMillis()}"

            val request = Request.Builder().url(url)
                .addHeader("Referer", refererUrl)
                .addHeader("cookie", CacheUtils.getCurrentCookies())
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .build()
            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: return@withContext null
            val json = JSONObject.parseObject(
                body.replace("cx_captcha_function(", "").replace(")", "")
            )
            val imgVo = json.getJSONObject("imageVerificationVo") ?: run {
                DebugLogCollector.w(TAG, "验证码响应无imageVerificationVo: ${body.take(200)}")
                return@withContext null
            }

            val shadeImage = imgVo.getString("shadeImage") ?: return@withContext null
            val cutoutImage = imgVo.getString("cutoutImage") ?: return@withContext null
            DebugLogCollector.d(TAG, "验证码图片获取成功: shade=${shadeImage.take(50)}..., cutout=${cutoutImage.take(50)}..., len=${shadeImage.length}/${cutoutImage.length}")
            return@withContext CaptchaData(
                captchaId = CAPTCHA_ID,
                type = type,
                version = version,
                token = json.getString("token") ?: return@withContext null,
                captchaKey = captchaKey,
                iv = iv,
                shadeImage = shadeImage,
                cutoutImage = cutoutImage
            )
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取验证码图片异常", e)
            null
        }
    }

    /**
     * 将base64图片数据解码为Bitmap
     */
    fun decodeBase64Image(base64: String): Bitmap? {
        return try {
            // 去掉可能的 data URI 前缀 (data:image/png;base64,)
            val cleanData = if (base64.contains(",")) base64.substringAfterLast(",") else base64
            val imageBytes = android.util.Base64.decode(cleanData, android.util.Base64.DEFAULT)
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e: Exception) {
            DebugLogCollector.w(TAG, "decodeBase64Image失败: ${e.message}, dataLen=${base64.length}")
            null
        }
    }

    /**
     * 验证滑块位置
     * @param xPosition 滑块x坐标(像素)
     * @param data CaptchaData
     * @return validate字符串(slide验证通过后的凭证), null=验证失败
     */
    suspend fun checkCaptchaResult(
        xPosition: Float,
        data: CaptchaData,
        courseId: Int = 0,
        classId: Int = 0,
        activeId: Long = 0,
        uid: String = ""
    ): String? = withContext(Dispatchers.IO) {
        try {
            val refUrl = "https://mobilelearn.chaoxing.com/newsign/preSign?&general=1&sys=1&ls=1&appType=15&isTeacherViewOpen=0" +
                    "&courseId=$courseId&classId=$classId&activePrimaryId=$activeId&uid=$uid"
            val url = "$URL_CAPTCHA_RESULT?callback=cx_captcha_function" +
                    "&captchaId=${data.captchaId}&type=${data.type}&token=${data.token}" +
                    "&textClickArr=[{\"x\":${xPosition.toInt()}}]" +
                    "&coordinate=[]&runEnv=10&version=${data.version}" +
                    "&t=a&iv=${data.iv}&_=${System.currentTimeMillis()}"

            val request = Request.Builder()
                .url(url)
                .addHeader("Referer", refUrl)
                .addHeader("cookie", CacheUtils.getCookies(uid))
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .build()

            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: return@withContext null
            val json = JSONObject.parseObject(
                body.replace("cx_captcha_function(", "").replace(")", "")
            )
            if (json.getIntValue("error") == 1) {
                DebugLogCollector.w(TAG, "验证码失败: ${json.getString("msg")}")
                return@withContext null
            }
            if (json.getBoolean("result") == true) {
                val extraData = json.getString("extraData") ?: return@withContext null
                return@withContext JSONObject.parseObject(
                    extraData.replace("\\\"", "\"")
                ).getString("validate")
            }
            return@withContext null
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "验证码验证异常", e)
            null
        }
    }

    private fun md5(value: String): String =
        MessageDigest.getInstance("MD5").digest(value.toByteArray(Charsets.UTF_8))
            .joinToString("") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }
}
