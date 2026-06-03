package com.cofbro.qian.scan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.cofbro.qian.databinding.ActivityScanBinding
import com.cofbro.qian.utils.GlideEngine
import com.cofbro.qian.utils.dp2px
import com.cofbro.qian.utils.getStatusBarHeight
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.king.wechat.qrcode.WeChatQRCodeDetector
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.opencv.OpenCV
import java.io.BufferedInputStream
import java.io.File


class ScanActivity : AppCompatActivity(), QRCodeView.Delegate {
    private companion object {
        const val TAG = "ScanActivity"
        const val REQUEST_CODE_PERMISSIONS = 1001
    }

    private var binding: ActivityScanBinding? = null
    private var hasCameraPermission = false
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = ActivityScanBinding.inflate(layoutInflater, null, false)
        setContentView(binding?.root)
        OpenCV.initAsync(this)
        WeChatQRCodeDetector.init(this)
        initView()
        initEvent()
        binding?.scanner?.setDelegate(this)
        checkAndRequestPermissions()
    }

    private fun initEvent() {
        binding?.ivToPhoto?.setOnClickListener {
            toGalleryAndDecode()
        }

        binding?.ivBack?.setOnClickListener {
            finish()
        }
    }

    private fun initView() {
        val margin = getStatusBarHeight(this) + dp2px(this, 6)
        val params = binding?.ivToPhoto?.layoutParams as? MarginLayoutParams
        params?.topMargin = margin
        binding?.ivToPhoto?.layoutParams = params
    }

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            hasCameraPermission = true
            startScanner()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasCameraPermission = true
                startScanner()
            } else {
                Toast.makeText(this, "需要相机权限才能扫码", Toast.LENGTH_LONG).show()
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "请在设置中开启相机权限", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun startScanner() {
        try {
            binding?.scanner?.startCamera()
            binding?.scanner?.startSpotAndShowRect()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start scanner", e)
            Toast.makeText(this, "相机启动失败", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onScanQRCodeSuccess(result: String?) {
        vibrate()
        sendResult(result)
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        var tipText: String? = binding?.scanner?.scanBoxView?.tipText
        if (tipText?.isEmpty() == true) return
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            if (!tipText!!.contains(ambientBrightnessTip)) {
                binding?.scanner?.scanBoxView?.tipText = tipText + ambientBrightnessTip
            }
        } else {
            if (tipText!!.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                binding?.scanner?.scanBoxView?.tipText = tipText
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
        Log.e(TAG, "Camera open error")
        Toast.makeText(this, "相机打开失败，请检查权限", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        if (hasCameraPermission) {
            startScanner()
        }
    }

    override fun onStop() {
        binding?.scanner?.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        binding?.scanner?.destroyDrawingCache()
        binding?.scanner?.onDestroy()
        super.onDestroy()
    }

    private fun toGalleryAndDecode() {
        PictureSelector
            .create(this)
            .openGallery(SelectMimeType.ofImage())
            .setSandboxFileEngine { context, srcPath, mineType, call ->
                if (call != null) {
                    val sandboxPath =
                        SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
                    call.onCallback(srcPath, sandboxPath)
                }
            }
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    result?.get(0)?.let { media ->
                        val path = media.availablePath
                        decodeFromPath(path)
                    }
                }

                override fun onCancel() {}
            })
    }

    private fun decodeFromPath(path: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val bitmap = loadBitmapFromPath(path)
            if (bitmap == null) {
                Log.e(TAG, "Failed to load bitmap from: $path")
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(this@ScanActivity, "图片加载失败", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }
            var result: String? = null
            try {
                val wxResults = WeChatQRCodeDetector.detectAndDecode(bitmap)
                result = wxResults.getOrNull(0)
                Log.d(TAG, "WeChatQRCode result: $result")
            } catch (e: Exception) {
                Log.w(TAG, "WeChatQRCode detect failed", e)
            }
            if (result.isNullOrEmpty()) {
                result = decodeWithZXing(bitmap)
                Log.d(TAG, "ZXing fallback result: $result")
            }
            val finalResult = result
            lifecycleScope.launch(Dispatchers.Main) {
                if (finalResult.isNullOrEmpty()) {
                    Toast.makeText(this@ScanActivity, "未识别到二维码", Toast.LENGTH_SHORT).show()
                } else {
                    sendResult(finalResult)
                }
            }
        }
    }

    private fun loadBitmapFromPath(path: String): Bitmap? {
        return try {
            if (path.startsWith("content://")) {
                contentResolver.openInputStream(Uri.parse(path))?.use { input ->
                    BufferedInputStream(input).use { bis ->
                        BitmapFactory.decodeStream(bis)
                    }
                }
            } else {
                val file = File(path)
                if (file.exists()) {
                    BitmapFactory.decodeFile(path)
                } else {
                    contentResolver.openInputStream(Uri.parse(path))?.use { input ->
                        BitmapFactory.decodeStream(input)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "loadBitmapFromPath error", e)
            null
        }
    }

    private fun decodeWithZXing(bitmap: Bitmap): String? {
        return try {
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            val source = RGBLuminanceSource(width, height, pixels)
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
            val reader = MultiFormatReader()
            val result = reader.decode(binaryBitmap)
            result.text
        } catch (e: Exception) {
            Log.w(TAG, "ZXing decode failed", e)
            null
        }
    }

    private fun sendResult(result: String?) {
        val intent = Intent()
        intent.putExtra("result", result)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }
}
