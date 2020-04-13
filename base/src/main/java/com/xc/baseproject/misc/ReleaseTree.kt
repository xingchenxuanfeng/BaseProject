package com.xc.baseproject.misc

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber
import java.lang.RuntimeException


class ReleaseTree : Timber.DebugTree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.WARN
    }

    override fun e(t: Throwable?) {
        super.e(t)
        FirebaseCrashlytics.getInstance().recordException(t ?: return)
    }

    override fun e(message: String?, vararg args: Any?) {
        super.e(message, *args)
        FirebaseCrashlytics.getInstance().recordException(RuntimeException(message + args.toString()))
    }

    override fun e(t: Throwable?, message: String?, vararg args: Any?) {
        super.e(t, message, *args)
        FirebaseCrashlytics.getInstance().recordException(RuntimeException(message + args.toString(), t))

    }

}