package com.xc.baseproject.extFunctions

import android.util.Log
import com.google.android.gms.common.internal.ApiExceptionUtil
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author xc
 * @time 19-4-15.
 */
fun <T> Observable<T>.netCompose(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun Disposable.addToDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}