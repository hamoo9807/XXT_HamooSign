package com.cofbro.qian.signer

import android.content.Context
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.DebugLogCollector

/**
 * 签到分发器
 * 根据签到类型选择对应的Signer
 */
object SignDispatcher {
    private const val TAG = "SignDispatcher"

    /**
     * 创建对应类型的Signer, 并执行签到
     *
     * @param aid 签到活动ID
     * @param signType otherId: 0=普通 2=二维码 3=手势 4=定位 5=签到码
     * @param context Context
     * @param enc QR码的enc参数(仅二维码签到需要)
     * @param fullIdParams QR码的完整参数(仅pre-release方式需要)
     * @param extraParams 额外参数(位置等)
     */
    suspend fun dispatch(
        aid: String,
        signType: String,
        context: Context,
        enc: String? = null,
        fullIdParams: String? = null,
        extraParams: Map<String, String> = emptyMap(),
        onSignResult: (suspend (SignResult) -> Unit)? = null
    ): SignResult {
        val uid = CacheUtils.cache["uid"] ?: ""
        val fid = CacheUtils.cache["fid"] ?: ""
        val name = CacheUtils.cache["username"] ?: ""

        DebugLogCollector.d(TAG, "分发签到: aid=$aid type=$signType")

        // 先预检查获取classId/courseId (用于后续preSign的extContent POST)
        val preCheck = SignPreCheck.fetch(aid)
        val classId = preCheck?.clazzId ?: extraParams["classId"] ?: ""
        val courseId = preCheck?.courseId ?: extraParams["courseId"] ?: ""
        val extContent = extraParams["extContent"] ?: ""
        val updatedExtraParams = extraParams + mapOf(
            "classId" to classId,
            "courseId" to courseId,
            "extContent" to extContent
        )

        val signer = createSigner(aid, signType, uid, fid, name, context, enc, fullIdParams, updatedExtraParams)

        // 设置已获取的preCheck, 避免重复请求
        signer.preCheck = preCheck

        // 预签到(现在classId/courseId已就绪, 可做extContent POST)
        signer.preSign()

        // 预检查(如果上面没获取到)
        if (signer.preCheck == null) signer.fetchPreCheck()

        // 预处理所有前置验证
        signer.prepareAll(context)

        // 执行签到
        val result = signer.sign()

        // 回调
        onSignResult?.invoke(result)

        return result
    }

    /**
     * 创建对应类型的Signer
     */
    fun createSigner(
        aid: String,
        signType: String,
        uid: String,
        fid: String,
        name: String,
        context: Context,
        enc: String? = null,
        fullIdParams: String? = null,
        extraParams: Map<String, String> = emptyMap()
    ): BaseSigner {
        val classId = extraParams["classId"] ?: ""
        val courseId = extraParams["courseId"] ?: ""
        val extContent = extraParams["extContent"] ?: ""
        return when (signType) {
            Constants.SIGN.SCAN_QR -> QRCodeSigner(aid, uid, fid, name, enc, fullIdParams, context, classId, courseId, extContent)
            Constants.SIGN.LOCATION -> LocationSigner(aid, uid, fid, name, context, classId, courseId, extContent)
            Constants.SIGN.GESTURE -> GestureSigner(aid, uid, fid, name, aid, classId, courseId, extContent)
            Constants.SIGN.SIGN_CODE -> PasswordSigner(aid, uid, fid, name, aid, classId, courseId, extContent)
            else -> NormalSigner(aid, uid, fid, name,
                lat = extraParams["lat"] ?: "-1",
                lon = extraParams["lon"] ?: "-1",
                addr = extraParams["addr"] ?: "",
                classId = classId, courseId = courseId, extContent = extContent
            )
        }
    }
}
