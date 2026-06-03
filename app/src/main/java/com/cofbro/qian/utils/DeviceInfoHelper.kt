package com.cofbro.qian.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaDrm
import android.os.Build
import android.provider.Settings
import android.util.Base64
import com.alibaba.fastjson.JSONObject
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Locale
import java.util.UUID
import javax.crypto.Cipher

/**
 * 设备信息收集 + RSA加密
 * 用于学习通人脸识别的设备指纹上报
 */
object DeviceInfoHelper {
    private const val PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC79d8Ot0hCbxxSISC6x8SCwTBspFSzlLKHJUYqoFNu1TSRaw4hEYkOnvEaL1VyoxV6HXcDrzwYvaFZaZaPQPFnfCHZy5dQwxcmifgSHqS+oKXw40Ys4cVIqnU5d90S7EWSRdBglX489jlqVaNcQSkDx2TYmC+DbAq9FV/BU09ISQIDAQAB"
    private const val RSA_PLAIN_BLOCK_SIZE = 117

    /**
     * 生成RSA加密的设备信息
     */
    fun buildEncryptedDeviceInfo(context: Context): String {
        return encryptByRsa(buildDeviceInfo(context).toJSONString().toByteArray(Charsets.UTF_8))
    }

    /**
     * 解析服务器返回的clientId, 获取cid和sc
     * clientId是RSA加密的设备信息JSON, 用公钥解密
     */
    fun decryptClientId(clientId: String): JSONObject? {
        return try {
            val encrypted = Base64.decode(clientId, Base64.DEFAULT)
            val publicKey = KeyFactory.getInstance("RSA").generatePublic(
                X509EncodedKeySpec(Base64.decode(PUBLIC_KEY, Base64.DEFAULT))
            ) as RSAPublicKey
            val blockSize = (publicKey.modulus.bitLength() + 7) / 8
            val output = ByteArrayOutputStream()
            for (offset in encrypted.indices step blockSize) {
                val block = BigInteger(1, encrypted.copyOfRange(offset, offset + blockSize))
                    .modPow(publicKey.publicExponent, publicKey.modulus).toByteArray()
                    .toFixedBlock(blockSize)
                require(block.size > 2 && block[0] == 0.toByte() && block[1] == 1.toByte())
                val separator = block.indexOf(0.toByte(), 2)
                require(separator > 2)
                output.write(block, separator + 1, block.size - separator - 1)
            }
            JSONObject.parseObject(output.toString(Charsets.UTF_8.name()))
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("HardwareIds")
    private fun buildDeviceInfo(context: Context): JSONObject {
        val packageInfo = try {
            context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
        } catch (e: Exception) { null }
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID).orEmpty()
        val deviceUniqueId = sha256("${context.packageName}:$androidId:${Build.FINGERPRINT}")
        val metrics = context.resources.displayMetrics

        return JSONObject().apply {
            put("deviceUniqueId", deviceUniqueId)
            put("cdid", deviceUniqueId)
            put("device_id", deviceUniqueId)
            put("android_id", androidId)
            put("mediaDrmId", getMediaDrmId())
            put("oaid", "")
            put("platform", "android")
            put("os_name", "android")
            put("os_ver", Build.VERSION.RELEASE.orEmpty())
            put("os_lang", Locale.getDefault().toLanguageTag())
            put("brand", Build.BRAND.orEmpty())
            put("board", Build.BOARD.orEmpty())
            put("hardware", Build.HARDWARE.orEmpty())
            put("model", Build.MODEL.orEmpty())
            put("cpu_ar", Build.SUPPORTED_ABIS.joinToString(","))
            put("app_name", context.packageName)
            put("app_ver", packageInfo?.versionName.orEmpty())
            put("versionCode", packageInfo?.versionCode?.toString().orEmpty())
            put("resolution", "${metrics.widthPixels}*${metrics.heightPixels}")
            put("dpi", metrics.density.toString())
            put("time_stamp", System.currentTimeMillis())
        }
    }

    private fun encryptByRsa(plain: ByteArray): String {
        val publicKey = KeyFactory.getInstance("RSA").generatePublic(
            X509EncodedKeySpec(Base64.decode(PUBLIC_KEY, Base64.DEFAULT))
        )
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val output = ByteArrayOutputStream()
        for (offset in plain.indices step RSA_PLAIN_BLOCK_SIZE) {
            val size = minOf(RSA_PLAIN_BLOCK_SIZE, plain.size - offset)
            output.write(cipher.doFinal(plain, offset, size))
        }
        return Base64.encodeToString(output.toByteArray(), Base64.NO_WRAP)
    }

    private fun getMediaDrmId(): String = try {
        val widevineUuid = UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L)
        val mediaDrm = MediaDrm(widevineUuid)
        try {
            Base64.encodeToString(mediaDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID), Base64.NO_WRAP)
        } finally {
            @Suppress("DEPRECATION")
            mediaDrm.release()
        }
    } catch (e: Exception) { "" }

    private fun ByteArray.toFixedBlock(size: Int): ByteArray = when {
        this.size == size -> this
        this.size == size + 1 && this[0] == 0.toByte() -> copyOfRange(1, this.size)
        this.size < size -> ByteArray(size - this.size) + this
        else -> error("Invalid RSA block size")
    }

    private fun ByteArray.indexOf(value: Byte, startIndex: Int): Int {
        for (index in startIndex until size) { if (this[index] == value) return index }
        return -1
    }

    private fun sha256(value: String): String = MessageDigest.getInstance("SHA-256").digest(value.toByteArray(Charsets.UTF_8))
        .joinToString("") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }

    /**
     * 生成faceEnc所需的signToken
     * signToken = MD5(fields排序后拼接 + cxtime + cxcid + sc)
     */
    fun generateSignToken(cxcid: String, sc: String, fields: Map<String, String>, cxtime: String): String {
        val signedFields = java.util.TreeMap<String, String>().apply {
            putAll(fields)
            put("cxtime", cxtime)
            put("cxcid", cxcid)
        }
        val raw = buildString {
            signedFields.forEach { (key, value) -> append(key); append(value) }
            append(sc)
        }
        return md5(raw)
    }

    private fun md5(value: String): String = MessageDigest.getInstance("MD5").digest(value.toByteArray(Charsets.UTF_8))
        .joinToString("") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }
}
