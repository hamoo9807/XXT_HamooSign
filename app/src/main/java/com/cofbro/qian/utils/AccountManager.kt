package com.cofbro.qian.utils

import android.content.Context
import android.util.Base64
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.data.URL
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AccountManager {
    private const val AES_KEY = "u2oh6Vu^HWe4_AES"

    /**
     * AES加密密码用于安全存储
     */
    fun encryptPassword(plain: String): String {
        if (plain.isEmpty()) return plain
        return try {
            val key = AES_KEY.toByteArray(Charsets.UTF_8)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE,
                SecretKeySpec(key, "AES"),
                IvParameterSpec(key))
            Base64.encodeToString(cipher.doFinal(plain.toByteArray(Charsets.UTF_8)), Base64.NO_WRAP)
        } catch (e: Exception) { plain }
    }

    /**
     * AES解密从存储中读取的密码
     * 兼容旧版未加密的密码 (解密失败时返回原文)
     */
    fun decryptPassword(encrypted: String): String {
        if (encrypted.isEmpty()) return encrypted
        return try {
            val key = AES_KEY.toByteArray(Charsets.UTF_8)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE,
                SecretKeySpec(key, "AES"),
                IvParameterSpec(key))
            String(cipher.doFinal(Base64.decode(encrypted, Base64.NO_WRAP)), Charsets.UTF_8)
        } catch (e: Exception) { encrypted }  // 兼容旧版明文密码
    }
    fun loadAllAccountData(context: Context, key: String =  Constants.RecycleJson.ACCOUNT_JSON_DATA): JSONObject {
        return Downloader.acquire(context, key)
            .safeParseToJson()
    }

    fun updateAccountData(context: Context, data: String, key: String =  Constants.RecycleJson.ACCOUNT_JSON_DATA) {
        Downloader.download(context, key, data)
    }

    fun buildCookieSignAccount(cookie: String, avatar: String, time: Long): JSONObject {
        val data = JSONObject()
        data[Constants.Account.USERNAME] = ""
        data[Constants.Account.PASSWORD] = ""
        data[Constants.Account.UID] = ""
        data[Constants.Account.COOKIE] = cookie
        data[Constants.Account.FID] = ""
        data[Constants.Account.TIME] = time
        data[Constants.Account.PIC_URL] = avatar
        return data
    }

    fun buildAccount(username: String, password: String, uid: String, fid: String, cookies:String): JSONObject {
        val data = JSONObject()
        data[Constants.Account.USERNAME] = username
        data[Constants.Account.PASSWORD] = encryptPassword(password)
        data[Constants.Account.UID] = uid
        data[Constants.Account.COOKIE] = cookies
        data[Constants.Account.FID] = fid
        data[Constants.Account.PIC_URL] = URL.getAvtarImgPath(uid)
        return data
    }

    fun bindAccounts(context: Context, loadedData: JSONObject?, newData: JSONObject, key: String = Constants.RecycleJson.ACCOUNT_JSON_DATA): JSONObject? {
        var data = loadedData
        val path = context.filesDir.path + File.separatorChar + key
        val file = File(path)
        if (file.exists()) {
            val newSize = data?.getIntExt(Constants.Account.SIZE).takeIf {
                it != -1
            } ?: 0
            // 检查是否重复 (同UID已存在则跳过)
            val array = data?.getJSONArrayExt(Constants.Account.USERS) ?: JSONArray()
            val newUid = newData.getString(Constants.Account.UID)
            if (!newUid.isNullOrEmpty()) {
                for (i in 0 until array.size) {
                    if (array.getJSONObject(i).getString(Constants.Account.UID) == newUid) {
                        DebugLogCollector.w("AccountManager", "账户已存在, 跳过: uid=$newUid")
                        return null  // 返回null让调用方知道绑定失败(账号已存在)
                    }
                }
            }
            array[newSize] = newData
            data?.set(Constants.Account.USERS, array)
            data?.set(Constants.Account.SIZE, newSize + 1)
        } else {
            file.createNewFile()
            data = JSONObject()
            val array = JSONArray()
            array[0] = newData
            data[Constants.Account.HISTORY] = "true"
            data[Constants.Account.SIZE] = 1
            data[Constants.Account.USERS] = array
        }
        // 立即持久化到文件
        updateAccountData(context, data.toString(), key)
        return data
    }
}