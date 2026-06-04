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
 *
 * ★ 注意: getCaptchaImage 有时返回base64, 有时返回图片URL(http开头),
 *   返回URL时由 SliderCaptchaDialog 直接加载网络图片
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
        /** 服务器时间戳(从getConf获取, checkResult需要) **/
        val serverT: Long,
        /** 滑块背景图(base64 或 http URL) **/
        val shadeImage: String,
        /** 滑块小块图(base64 或 http URL) **/
        val cutoutImage: String
    )

    /**
     * 获取验证码服务器时间戳
     */
    private suspend fun getCaptchaConf(): Long? = withContext(Dispatchers.IO) {
        try {
            val url = "$URL_CAPTCHA_CONF?callback=cx_captcha_function&captchaId=$CAPTCHA_ID&_=${System.currentTimeMillis()}"
            DebugLogCollector.d(TAG, "getCaptchaConf 请求URL: ${url.take(120)}...")
            val request = Request.Builder().url(url)
                .addHeader("Referer", "https://mobilelearn.chaoxing.com/")
                .addHeader("cookie", CacheUtils.getCurrentCookies())
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .build()
            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: run {
                DebugLogCollector.e(TAG, "getCaptchaConf: 响应body为空")
                return@withContext null
            }
            DebugLogCollector.d(TAG, "getCaptchaConf raw: ${body.take(300)}")
            val cleanedBody = body
                .replace("cx_captcha_function(", "")
                .replace(")", "")
                .trim()
            val json = JSONObject.parseObject(cleanedBody)
            val t = json.getLong("t")
            DebugLogCollector.d(TAG, "getCaptchaConf 解析结果: t=$t, json=${cleanedBody.take(200)}")
            return@withContext if (t > 0) t else {
                DebugLogCollector.e(TAG, "getCaptchaConf: t值无效: $t")
                null
            }
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取验证码配置异常: ${e.javaClass.simpleName} - ${e.message}", e)
            null
        }
    }

    /**
     * 获取滑块验证码图片数据 (CSF对齐: getCaptchaImageV2)
     *
     * 关键区别: 请求中必须带 referer 查询参数指向 preSign URL,
     * 服务器用此验证请求是否来自合法的签到页面, 否则返回 {"10030":"r verify error"}
     *
     * 返回值: shadeImage/cutoutImage 可能是 base64 或 http URL,
     *   由 SliderCaptchaDialog 自动识别处理
     */
    suspend fun getCaptchaImage(
        courseId: String = "",
        classId: String = "",
        activeId: String = "",
        uid: String = ""
    ): CaptchaData? = withContext(Dispatchers.IO) {
        try {
            DebugLogCollector.d(TAG, "========== getCaptchaImage 开始 ==========")
            DebugLogCollector.d(TAG, "参数: courseId=$courseId, classId=$classId, activeId=$activeId, uid=${uid.take(10)}...")

            val t = getCaptchaConf() ?: run {
                DebugLogCollector.e(TAG, "getCaptchaImage: getCaptchaConf返回null, 无法继续")
                return@withContext null
            }
            DebugLogCollector.d(TAG, "获取到服务器时间戳 t=$t")
            val type = "slide"
            val version = "1.1.20"
            val captchaKey = md5("$t${UUID.randomUUID()}")
            val iv = md5("$CAPTCHA_ID$type${System.currentTimeMillis()}${UUID.randomUUID()}")
            val token = md5("$t$CAPTCHA_ID$type$captchaKey") + ":${t + 300000L}"

            DebugLogCollector.d(TAG, "生成参数: captchaKey=${captchaKey.take(20)}..., iv=${iv.take(20)}..., token=${token.take(40)}...")

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

            DebugLogCollector.d(TAG, "请求验证码图片 URL: ${url.take(200)}...")
            DebugLogCollector.d(TAG, "Referer: ${refererUrl.take(150)}...")

            val request = Request.Builder().url(url)
                .addHeader("Referer", refererUrl)
                .addHeader("cookie", CacheUtils.getCurrentCookies())
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .build()
            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: run {
                DebugLogCollector.e(TAG, "验证码图片响应body为空")
                return@withContext null
            }
            DebugLogCollector.d(TAG, "验证码图片响应: ${body.take(500)}")
            val json = JSONObject.parseObject(
                body.replace("cx_captcha_function(", "").replace(")", "")
            )
            val imgVo = json.getJSONObject("imageVerificationVo") ?: run {
                DebugLogCollector.e(TAG, "验证码响应无imageVerificationVo, 完整body: ${body.take(500)}")
                return@withContext null
            }

            val shadeImage = imgVo.getString("shadeImage") ?: run {
                DebugLogCollector.e(TAG, "shadeImage字段为空")
                return@withContext null
            }
            val cutoutImage = imgVo.getString("cutoutImage") ?: run {
                DebugLogCollector.e(TAG, "cutoutImage字段为空")
                return@withContext null
            }
            DebugLogCollector.d(TAG, "验证码图片获取成功!")
            DebugLogCollector.d(TAG, "  shadeImage: 长度=${shadeImage.length}, isUrl=${shadeImage.startsWith("http")}, 前缀=${shadeImage.take(80)}")
            DebugLogCollector.d(TAG, "  cutoutImage: 长度=${cutoutImage.length}, isUrl=${cutoutImage.startsWith("http")}, 前缀=${cutoutImage.take(80)}")

            return@withContext CaptchaData(
                captchaId = CAPTCHA_ID,
                type = type,
                version = version,
                token = json.getString("token") ?: run {
                    DebugLogCollector.e(TAG, "响应中token字段为空")
                    return@withContext null
                },
                captchaKey = captchaKey,
                iv = iv,
                serverT = t,
                shadeImage = shadeImage,
                cutoutImage = cutoutImage
            )
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取验证码图片异常: ${e.javaClass.simpleName} - ${e.message}", e)
            null
        }
    }

    /**
     * 将base64图片数据解码为Bitmap
     */
    fun decodeBase64Image(base64: String): Bitmap? {
        return try {
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
            DebugLogCollector.d(TAG, "========== checkCaptchaResult 开始 ==========")
            DebugLogCollector.d(TAG, "xPosition=${"%.1f".format(xPosition)} -> intValue=${xPosition.toInt()}, courseId=$courseId, classId=$classId, activeId=$activeId, uid=${uid.take(10)}...")

            val refUrl = "https://mobilelearn.chaoxing.com/newsign/preSign?&general=1&sys=1&ls=1&appType=15&isTeacherViewOpen=0" +
                    "&courseId=$courseId&classId=$classId&activePrimaryId=$activeId&uid=$uid"

            // ★ 对齐 ChaoxingSignFaker: t="a"(硬编码), x=toInt(), 不带captchaKey
            val textClickArrRaw = "[{\"x\":${xPosition.toInt()}}]"
            DebugLogCollector.d(TAG, "textClickArr: $textClickArrRaw")

            val url = "$URL_CAPTCHA_RESULT?callback=cx_captcha_function" +
                    "&captchaId=${data.captchaId}&type=${data.type}&token=${data.token}" +
                    "&textClickArr=${java.net.URLEncoder.encode(textClickArrRaw, "UTF-8")}" +
                    "&coordinate=${java.net.URLEncoder.encode("[]", "UTF-8")}" +
                    "&runEnv=10&version=${data.version}" +
                    "&t=a&iv=${data.iv}" +
                    "&_=${System.currentTimeMillis()}"
                    "&_=${System.currentTimeMillis()}"

            DebugLogCollector.d(TAG, "验证请求URL: ${url.take(300)}...")
            DebugLogCollector.d(TAG, "验证请求 x=${xPosition.toInt()}, t=a")

            val request = Request.Builder()
                .url(url)
                .addHeader("Referer", refUrl)
                .addHeader("cookie", CacheUtils.getCookies(uid))
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .build()

            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: run {
                DebugLogCollector.e(TAG, "验证响应body为空")
                return@withContext null
            }
            DebugLogCollector.d(TAG, "验证响应body(前500字): ${body.take(500)}")

            // 判断是否返回了JSONP还是HTML错误页
            if (!body.contains("cx_captcha_function")) {
                DebugLogCollector.e(TAG, "响应不是JSONP格式! 可能是HTTP错误页, body前200字: ${body.take(200)}")
                return@withContext null
            }

            val json = JSONObject.parseObject(
                body.replace("cx_captcha_function(", "").replace(")", "")
            )
            DebugLogCollector.d(TAG, "解析后json: error=${json.getIntValue("error")}, result=${json.getBoolean("result")}, msg=${json.getString("msg")}")

            if (json.getIntValue("error") == 1) {
                DebugLogCollector.w(TAG, "验证码服务端返回错误: error=1, msg=${json.getString("msg")}")
                return@withContext null
            }
            if (json.getBoolean("result") == true) {
                val extraData = json.getString("extraData") ?: run {
                    DebugLogCollector.e(TAG, "验证成功但extraData为空, json全量: ${json.toJSONString().take(400)}")
                    return@withContext null
                }
                DebugLogCollector.d(TAG, "extraData原始: ${extraData.take(200)}")
                val parsed = JSONObject.parseObject(extraData.replace("\\\"", "\""))
                val validate = parsed.getString("validate")
                DebugLogCollector.d(TAG, "========== checkCaptchaResult 成功! validate=${validate?.take(30)}... ==========")
                return@withContext validate
            }
            DebugLogCollector.w(TAG, "验证结果: result=false 或 error/result均未命中, json=${json.toJSONString().take(300)}")
            return@withContext null
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "验证码验证异常: ${e.javaClass.simpleName} - ${e.message}", e)
            null
        }
    }

    private fun md5(value: String): String =
        MessageDigest.getInstance("MD5").digest(value.toByteArray(Charsets.UTF_8))
            .joinToString("") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }
}
