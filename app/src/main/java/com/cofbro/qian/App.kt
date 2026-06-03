package com.cofbro.qian

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.cofbro.qian.data.URL
import com.cofbro.qian.update.InstallCompleteReceiver
import com.cofbro.qian.utils.AmapKeyStore
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.CookieRefresher
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.SignFilterManager
import com.hjq.toast.ToastUtils
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

class App : Application(), Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
        NetworkUtils.init(this)
        CookieRefresher.init(this)
        CacheUtils.init(this)
        CacheUtils.cache[Constants.DataLoad.FIRST_LOAD] = Constants.DataLoad.UNLOAD
        AmapKeyStore.init(this)
        SignFilterManager.init(this)
        registerInstallPackageReceiver()
        initHyphenateSDK()
    }

    private fun initHyphenateSDK() {
        val options = EMOptions()
        options.appKey = URL.IM_APP_KEY
        options.autoLogin = false
        options.acceptInvitationAlways = false
        EMClient.getInstance().init(this, options)
        EMClient.getInstance().setDebugMode(false)
    }

    private fun registerInstallPackageReceiver() {
        val installCompleteReceiver = InstallCompleteReceiver()
        val filter = IntentFilter(Intent.ACTION_PACKAGE_REPLACED)
        filter.addDataScheme("package")
        registerReceiver(installCompleteReceiver, filter)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        CacheUtils.activities[Constants.Cache.TOP_ACTIVITY] = activity
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}