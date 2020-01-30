package com.xc.baseproject

import android.app.Application
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI
import com.avos.avoscloud.AVOSCloud
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import com.ut.mini.UTAnalytics
import com.xc.baseproject.constants.LEANCLOUD_APP_ID
import com.xc.baseproject.constants.LEANCLOUD_APP_Key
import com.xc.baseproject.misc.ReleaseTree
import com.xc.baseproject.push.PushManager
import io.fabric.sdk.android.Fabric
import timber.log.Timber

const val aliyunAppKey = "28325271"
const val aliyunAppSecret = "f6bffa000b5f82927d1cab9db9d95be6"

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppUtil.appContext = applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
        Fabric.with(this.applicationContext, Crashlytics(), CrashlyticsNdk())

        AVOSCloud.initialize(this, LEANCLOUD_APP_ID, LEANCLOUD_APP_Key)
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG)

        PushManager.init(this)
        FeedbackAPI.init(this, aliyunAppKey, aliyunAppSecret)
    }

}