package com.xc.baseproject.misc

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber
import java.lang.RuntimeException


class ReleaseTree : Timber.DebugTree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.WARN
    }

    override fun e(t: Throwable?) {
        Crashlytics.logException(t)
        super.e(t)
    }

    override fun e(t: Throwable?, message: String?, vararg args: Any?) {
        Crashlytics.logException(RuntimeException(message + args.toString(), t))
        super.e(t, message, *args)
    }

    override fun e(message: String?, vararg args: Any?) {
        Crashlytics.logException(RuntimeException(message + args.toString()))
        super.e(message, *args)
    }
}