package com.cofbro.qian.view.dialog

import android.app.Dialog
import android.content.Context
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cofbro.qian.R
import com.cofbro.qian.utils.CaptchaHelper
import com.cofbro.qian.utils.DebugLogCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 滑块验证码弹窗 (CSF对齐版)
 * - 图片支持 URL 和 base64 两种格式 (优先判断 http 前缀)
 * - 滑块用SeekBar (系统组件, 触摸准确)
 * - 支持关闭按钮 + 返回键退出, 不再卡死
 */
class SliderCaptchaDialog(
    context: Context,
    private val captchaData: CaptchaHelper.CaptchaData,
    private val activeId: String = "",
    private val uid: String = "",
    private val courseId: String = "",
    private val classId: String = "",
    private val onResult: (String?) -> Unit
) : Dialog(context) {

    companion object {
        private const val TAG = "SliderCaptcha"
    }

    private var ivShade: ImageView? = null
    private var ivCutout: ImageView? = null
    private var seekBar: SeekBar? = null
    private var tvHint: TextView? = null
    private var btnConfirm: Button? = null
    private var ivClose: ImageView? = null
    private var isLoading = true
    private var hasResult = false  // 防止重复回调

    init {
        DebugLogCollector.d(TAG, "========== SliderCaptchaDialog 初始化 ==========")
        DebugLogCollector.d(TAG, "captchaId=${captchaData.captchaId}, type=${captchaData.type}, version=${captchaData.version}")
        DebugLogCollector.d(TAG, "token=${captchaData.token.take(40)}..., captchaKey=${captchaData.captchaKey.take(20)}...")
        DebugLogCollector.d(TAG, "activeId=$activeId, uid=${uid.take(10)}..., courseId=$courseId, classId=$classId")
        DebugLogCollector.d(TAG, "shadeImage=${captchaData.shadeImage.take(80)}... (len=${captchaData.shadeImage.length})")
        DebugLogCollector.d(TAG, "cutoutImage=${captchaData.cutoutImage.take(80)}... (len=${captchaData.cutoutImage.length})")
        DebugLogCollector.d(TAG, "图片类型判断: shadeIsUrl=${captchaData.shadeImage.startsWith("http")}, cutoutIsUrl=${captchaData.cutoutImage.startsWith("http")}")

        setContentView(R.layout.dialog_captcha_slider)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)

        ivShade = findViewById(R.id.iv_shade_image)
        ivCutout = findViewById(R.id.iv_cutout_image)
        seekBar = findViewById(R.id.seekbar_captcha)
        tvHint = findViewById(R.id.tv_slider_hint)
        btnConfirm = findViewById(R.id.btn_captcha_confirm)
        ivClose = findViewById(R.id.iv_captcha_close)

        DebugLogCollector.d(TAG, "View绑定完成: shade=${ivShade != null}, cutout=${ivCutout != null}, seekBar=${seekBar != null}, btn=${btnConfirm != null}, close=${ivClose != null}")

        // ★ 关闭按钮: 用户主动退出, 回调 null
        ivClose?.setOnClickListener {
            DebugLogCollector.d(TAG, "用户点击关闭按钮, 取消验证码")
            dismissWithCancel()
        }

        // ★ Dialog取消监听: 用户按返回键时触发
        setOnCancelListener {
            DebugLogCollector.d(TAG, "Dialog onCancel 触发")
            if (!hasResult) {
                DebugLogCollector.w(TAG, "验证码被取消(未完成验证), 回调 null")
                hasResult = true
                onResult(null)
            }
        }

        // 加载图片: 自动识别 URL 或 base64
        loadImages()

        // SeekBar监听
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val cutoutMax = (ivShade?.width ?: 280) - (ivCutout?.width ?: 56)
                    if (cutoutMax > 0) {
                        val transX = progress / 1000f * cutoutMax
                        ivCutout?.translationX = transX
                        DebugLogCollector.d(TAG, "SeekBar拖动: progress=$progress/1000, cutoutMax=$cutoutMax, transX=${"%.1f}".format(transX)}")
                    } else {
                        DebugLogCollector.d(TAG, "SeekBar拖动: progress=$progress/1000, cutoutMax=0 (view未布局?) shadeW=${ivShade?.width}, cutW=${ivCutout?.width}")
                    }
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                DebugLogCollector.d(TAG, "SeekBar开始触摸: progress=${seekBar?.progress}")
                tvHint?.visibility = View.GONE
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val pct = seekBar?.progress?.div(10) ?: 0
                DebugLogCollector.d(TAG, "SeekBar停止触摸: progress=${seekBar?.progress}/1000 (${pct}%), 启用确认按钮")
                btnConfirm?.isEnabled = true
                tvHint?.text = "已拖动到 ${pct}%"
                tvHint?.visibility = View.VISIBLE
            }
        })

        btnConfirm?.setOnClickListener {
            if (isLoading) {
                DebugLogCollector.w(TAG, "确认按钮点击被忽略: 图片仍在加载中")
                return@setOnClickListener
            }
            btnConfirm?.isEnabled = false
            btnConfirm?.text = "验证中..."
            val progress = seekBar?.progress ?: 0

            // CSF坐标转换: (sliderPos / maxRange) * 280 - 8  (对齐 ChaoxingSignFaker)
            val shadeWidth = (ivShade?.width ?: 280).toFloat()
            val cutoutWidth = (ivCutout?.width ?: 56).toFloat()
            val maxRange = (shadeWidth - cutoutWidth).coerceAtLeast(1f)
            val ratio = progress / 1000f
            val resolvedX = (ratio * 280f) - 8f

            DebugLogCollector.d(TAG, "========== 开始验证 ==========")
            DebugLogCollector.d(TAG, "SeekBar progress=$progress/1000, ratio=${"%.4f".format(ratio)}")
            DebugLogCollector.d(TAG, "View尺寸: shadeW=$shadeWidth, cutoutW=$cutoutWidth, maxRange=${"%.1f".format(maxRange)}")
            DebugLogCollector.d(TAG, "转换后坐标: resolvedX=${"%.1f".format(resolvedX)} (公式: ratio*280-8)")

            CoroutineScope(Dispatchers.IO).launch {
                val validate = CaptchaHelper.checkCaptchaResult(
                    resolvedX, captchaData,
                    courseId = courseId.toIntOrNull() ?: 0,
                    classId = classId.toIntOrNull() ?: 0,
                    activeId = activeId.toLongOrNull() ?: 0L,
                    uid = uid
                )
                withContext(Dispatchers.Main) {
                    if (validate != null) {
                        DebugLogCollector.d(TAG, "========== 验证成功! validate=${validate.take(30)}... ==========")
                        hasResult = true
                        onResult(validate)
                        dismiss()
                    } else {
                        DebugLogCollector.w(TAG, "验证失败: checkCaptchaResult 返回 null")
                        DebugLogCollector.w(TAG, "参数回顾 - progress=$progress, resolvedX=${"%.1f".format(resolvedX)}, courseId=$courseId, classId=$classId, activeId=$activeId")
                        Toast.makeText(context, "验证失败, 请重试", Toast.LENGTH_SHORT).show()
                        btnConfirm?.isEnabled = false
                        btnConfirm?.text = "确认"
                        seekBar?.progress = 0
                        ivCutout?.translationX = 0f
                        tvHint?.text = "请滑动滑块完成验证"
                        tvHint?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    /**
     * 用户主动关闭(点X或按返回键): 回调 null 通知调用方
     */
    private fun dismissWithCancel() {
        if (!hasResult) {
            DebugLogCollector.d(TAG, "dismissWithCancel: 用户取消验证码")
            hasResult = true
            onResult(null)
        }
        dismiss()
    }

    override fun onBackPressed() {
        DebugLogCollector.d(TAG, "onBackPressed: 用户按返回键, 取消验证码")
        dismissWithCancel()
    }

    /**
     * 加载验证码图片: 自动识别 URL 或 base64
     * - URL (http开头) → Glide 直接加载网络图片
     * - base64 → 解码后用 Glide 加载 byte array
     *
     * ★ 修复: 服务器可能返回图片URL而非base64数据,
     *   之前代码一律当base64解码, 导致加载垃圾数据, 用户看到空白
     */
    private fun loadImages() {
        tvHint?.text = "加载验证码..."

        val shadeIsUrl = captchaData.shadeImage.startsWith("http")
        val cutoutIsUrl = captchaData.cutoutImage.startsWith("http")

        DebugLogCollector.d(TAG, "loadImages: shadeIsUrl=$shadeIsUrl, cutoutIsUrl=$cutoutIsUrl")

        if (shadeIsUrl || cutoutIsUrl) {
            // ★ 服务器返回URL → Glide直接加载网络图片 (最简单可靠)
            DebugLogCollector.d(TAG, "loadImages: URL模式, Glide直接加载网络图片")
            DebugLogCollector.d(TAG, "  shadeUrl=$captchaData.shadeImage")
            DebugLogCollector.d(TAG, "  cutoutUrl=$captchaData.cutoutImage")

            try {
                Glide.with(context)
                    .load(captchaData.shadeImage)
                    .into(ivShade!!)
                Glide.with(context)
                    .load(captchaData.cutoutImage)
                    .into(ivCutout!!)
                isLoading = false
                tvHint?.text = "请滑动滑块完成验证"
                DebugLogCollector.d(TAG, "URL图片加载请求已发送, 用户可开始滑动")
            } catch (e: Exception) {
                DebugLogCollector.e(TAG, "URL图片加载异常: ${e.javaClass.simpleName} - ${e.message}", e)
                tvHint?.text = "验证码加载失败: ${e.message}"
            }
        } else {
            // ★ 服务器返回base64 → 解码后加载
            DebugLogCollector.d(TAG, "loadImages: base64模式, 开始解码...")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val shadeBytes = decodeBase64ToBytes(captchaData.shadeImage)
                    val cutoutBytes = decodeBase64ToBytes(captchaData.cutoutImage)
                    DebugLogCollector.d(TAG, "base64解码结果: shade=${shadeBytes?.size ?: "null"}bytes, cutout=${cutoutBytes?.size ?: "null"}bytes")

                    withContext(Dispatchers.Main) {
                        if (shadeBytes != null && cutoutBytes != null) {
                            DebugLogCollector.d(TAG, "Glide加载base64图片到ImageView...")
                            Glide.with(context)
                                .load(shadeBytes)
                                .into(ivShade!!)
                            Glide.with(context)
                                .load(cutoutBytes)
                                .into(ivCutout!!)
                            isLoading = false
                            tvHint?.text = "请滑动滑块完成验证"
                            DebugLogCollector.d(TAG, "base64图片加载完成, 用户可开始滑动")
                        } else {
                            DebugLogCollector.e(TAG, "base64解码失败: shade=${shadeBytes != null}, cutout=${cutoutBytes != null}")
                            tvHint?.text = "验证码加载失败"
                        }
                    }
                } catch (e: Exception) {
                    DebugLogCollector.e(TAG, "base64图片加载异常: ${e.javaClass.simpleName} - ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        tvHint?.text = "验证码加载失败: ${e.message}"
                    }
                }
            }
        }
    }

    private fun decodeBase64ToBytes(base64: String): ByteArray? {
        return try {
            val cleanData = if (base64.contains(",")) base64.substringAfterLast(",") else base64
            DebugLogCollector.d(TAG, "decodeBase64: 原始长度=${base64.length}, 清理后长度=${cleanData.length}, 前缀=${base64.take(30)}")
            Base64.decode(cleanData, Base64.DEFAULT)
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "Base64.decode异常: ${e.javaClass.simpleName} - ${e.message}, 数据前缀=${base64.take(50)}")
            null
        }
    }
}
