package com.xc.baseproject

import android.app.Application
import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.AVObject
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import com.xc.baseproject.constants.LEANCLOUD_APP_ID
import com.xc.baseproject.constants.LEANCLOUD_APP_Key
import com.xc.baseproject.misc.ReleaseTree
import io.fabric.sdk.android.Fabric
import timber.log.Timber

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Util.appContext = applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
        Fabric.with(this.applicationContext, Crashlytics(), CrashlyticsNdk())

        AVOSCloud.initialize(this, LEANCLOUD_APP_ID, LEANCLOUD_APP_Key)
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG)
    }
}