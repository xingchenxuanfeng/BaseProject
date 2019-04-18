package com.xc.baseproject.misc

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber


class ReleaseTree : Timber.DebugTree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.WARN
    }

    override fun e(t: Throwable?) {
        Crashlytics.logException(t)
        super.e(t)
    }
}