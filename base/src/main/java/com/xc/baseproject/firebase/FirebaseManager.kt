package com.xc.baseproject.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.xc.baseproject.AppUtil

object FirebaseManager {
    fun init() {
        initFirebaseCrashlytics()
    }

    private fun initFirebaseCrashlytics() {
        FirebaseApp.initializeApp(AppUtil.appContext)
        FirebaseCrashlytics.getInstance().setUserId(AppUtil.getUtDid())
        FirebaseCrashlytics.getInstance().setCustomKey("channel", AppUtil.appChannel)
    }
}