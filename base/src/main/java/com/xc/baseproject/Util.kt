package com.xc.baseproject

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.content.Context
import com.xc.baseproject.misc.LiveDataToObservableException
import io.reactivex.Observable

@SuppressLint("StaticFieldLeak")
object Util {
    lateinit var appContext: Context
        internal set

}

fun <T> LiveData<T>.toObservable(lifecycleOwner: LifecycleOwner, valueIfNull: T? = null): Observable<T>? {
    return Observable.create { observer ->
        observe(lifecycleOwner, android.arch.lifecycle.Observer { t: T? ->
            if (!observer.isDisposed) {
                if (t != null) {
                    observer.onNext(t)
                } else {
                    if (valueIfNull != null) {
                        observer.onNext(valueIfNull)
                    } else {
                        observer.onError(LiveDataToObservableException(
                                "convert liveData value t to RxJava onNext(t), t cannot be null"))
                    }
                }
            }
        })
    }
}