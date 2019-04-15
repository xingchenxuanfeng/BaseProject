package com.xc.baseproject.extFunctions

import android.util.Log
import com.crashlytics.android.Crashlytics
import com.google.android.gms.common.internal.ApiExceptionUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author xc
 * @time 19-4-15.
 */
fun <T> Observable<T>.netCompose(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                Timber.e(it)
                Crashlytics.logException(it)
                return@onErrorReturn null
            }
}