package com.cofbro.qian.signer

import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 普通签到(普通0 + 拍照型普通)
 */
class NormalSigner(
    override val aid: String,
    override val uid: String = CacheUtils.cache["uid"] ?: "",
    override val fid: String = CacheUtils.cache["fid"] ?: "",
    override val name: String = CacheUtils.cache["username"] ?: "",
    val lat: String = "-1",
    val lon: String = "-1",
    val addr: String = "",
    override val classId: String = "",
    override val courseId: String = "",
    override val extContent: String = ""
) : BaseSigner(aid, uid, fid, name, classId, courseId, extContent) {

    companion object {
        private const val TAG = "NormalSigner"
    }

    override suspend fun checkAlreadySigned(preSignBody: String): Boolean = false

    override suspend fun sign(): SignResult = withContext(Dispatchers.IO) {
        DebugLogCollector.d(TAG, "===== NormalSigner.sign() =====")

        val needPhoto = preCheck?.needPhoto == true

        // 需要拍照 → 预上传照片
        if (needPhoto && preUploadedObjectId == null) {
            val (success, objId) = NetworkUtils.preUploadPhoto()
            if (success) preUploadedObjectId = objId
        }

        // 先POST
        val pResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name,
            latitude = lat, longitude = lon, address = addr,
            objectId = preUploadedObjectId)
        val pBody = pResp.data?.body?.string() ?: ""
        DebugLogCollector.d(TAG, "POST签到: $pBody")
        val pResult = SignResult.fromBody(pBody)
        if (pResult.isSuccess()) return@withContext pResult

        // validate_处理
        if (pResult.validateToken != null)
            return@withContext handleValidate(pResult.validateToken!!, lat, lon, addr)

        // checkFace_处理
        if (pResult.faceToken != null)
            return@withContext handleCheckFace(pResult.faceToken!!, lat, lon, addr)

        // POST失败, 尝试GET
        val gResp = NetworkUtils.getSign(aid = aid, uid = uid, fid = fid, name = name,
            latitude = lat, longitude = lon, address = addr,
            objectId = preUploadedObjectId)
        val gBody = gResp.data?.body?.string() ?: ""
        DebugLogCollector.d(TAG, "GET签到: $gBody")
        return@withContext handleSignResponse(gBody, lat, lon, addr)
    }
}
