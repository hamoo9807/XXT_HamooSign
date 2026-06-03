package com.cofbro.qian.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cofbro.qian.R
import com.cofbro.qian.utils.CaptchaHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream

/**
 * 滑块验证码弹窗 (CSF对齐版)
 * - 图片用Glide加载 (支持base64 data URI)
 * - 滑块用SeekBar (系统组件, 触摸准确)
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

    private var ivShade: ImageView? = null
    private var ivCutout: ImageView? = null
    private var seekBar: SeekBar? = null
    private var tvHint: TextView? = null
    private var btnConfirm: Button? = null
    private var isLoading = true

    init {
        setContentView(R.layout.dialog_captcha_slider)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)

        ivShade = findViewById(R.id.iv_shade_image)
        ivCutout = findViewById(R.id.iv_cutout_image)
        seekBar = findViewById(R.id.seekbar_captcha)
        tvHint = findViewById(R.id.tv_slider_hint)
        btnConfirm = findViewById(R.id.btn_captcha_confirm)

        // 加载图片: 尝试Glide → 失败则手动解码base64
        loadImages()

        // SeekBar监听
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // 滑块小块图跟随移动: progress 0~1000 映射到 shadeImage宽度
                    val cutoutMax = (ivShade?.width ?: 280) - (ivCutout?.width ?: 56)
                    if (cutoutMax > 0) {
                        ivCutout?.translationX = (progress / 1000f * cutoutMax)
                    }
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                tvHint?.visibility = View.GONE
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                btnConfirm?.isEnabled = true
                tvHint?.text = "已拖动到 ${seekBar?.progress?.div(10)}%"
                tvHint?.visibility = View.VISIBLE
            }
        })

        btnConfirm?.setOnClickListener {
            if (isLoading) return@setOnClickListener
            btnConfirm?.isEnabled = false
            btnConfirm?.text = "验证中..."
            val progress = seekBar?.progress ?: 0
            // CSF坐标转换: progress 0~1000 → 0~280 像素 → -8偏移
            val shadeWidth = (ivShade?.width ?: 280).toFloat()
            val cutoutWidth = (ivCutout?.width ?: 56).toFloat()
            val maxRange = (shadeWidth - cutoutWidth).coerceAtLeast(1f)
            val ratio = progress / 1000f
            val resolvedX = (ratio * 280f) - 8f

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
                        onResult(validate)
                        dismiss()
                    } else {
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

    private fun loadImages() {
        tvHint?.text = "加载验证码..."

        // 转换base64为 byte array → Glide 加载
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val shadeBytes = decodeBase64ToBytes(captchaData.shadeImage)
                val cutoutBytes = decodeBase64ToBytes(captchaData.cutoutImage)

                withContext(Dispatchers.Main) {
                    if (shadeBytes != null && cutoutBytes != null) {
                        Glide.with(context)
                            .load(shadeBytes)
                            .into(ivShade!!)
                        Glide.with(context)
                            .load(cutoutBytes)
                            .into(ivCutout!!)
                        isLoading = false
                        tvHint?.text = "请滑动滑块完成验证"
                    } else {
                        tvHint?.text = "验证码加载失败"
                        android.util.Log.e("SliderCaptcha", "base64解码失败")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvHint?.text = "验证码加载失败: ${e.message}"
                    android.util.Log.e("SliderCaptcha", "加载异常", e)
                }
            }
        }
    }

    private fun decodeBase64ToBytes(base64: String): ByteArray? {
        return try {
            val cleanData = if (base64.contains(",")) base64.substringAfterLast(",") else base64
            Base64.decode(cleanData, Base64.DEFAULT)
        } catch (e: Exception) { null }
    }

    override fun onBackPressed() {}
}
