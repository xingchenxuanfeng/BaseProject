package com.xc.baseproject.misc

import android.util.Log
import com.alibaba.ha.adapter.service.tlog.TLogService
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.xc.baseproject.BuildConfig
import timber.log.Timber
import java.lang.RuntimeException


class ReleaseTree : Timber.DebugTree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        if (BuildConfig.DEBUG) {
            return true
        }
        return priority >= Log.WARN
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)

        when (priority) {
            Log.VERBOSE ->
                TLogService.logv("Timber.ReleaseTree", tag, "$message exception:${t?.message}")
            Log.DEBUG ->
                TLogService.logd("Timber.ReleaseTree", tag, "$message exception:${t?.message}")
            Log.INFO ->
                TLogService.logi("Timber.ReleaseTree", tag, "$message exception:${t?.message}")
            Log.WARN ->
                TLogService.logw("Timber.ReleaseTree", tag, "$message exception:${t?.message}")
            Log.ERROR, Log.ASSERT ->
                TLogService.loge("Timber.ReleaseTree", tag, "$message exception:${t?.message}")

        }
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