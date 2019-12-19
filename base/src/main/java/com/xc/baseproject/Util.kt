package com.xc.baseproject

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.xc.baseproject.misc.LiveDataToObservableException
import io.reactivex.Observable

fun <T> LiveData<T>.toObservable(lifecycleOwner: LifecycleOwner, valueIfNull: T? = null): Observable<T>? {
    return Observable.create { observer ->
        observe(lifecycleOwner, androidx.lifecycle.Observer { t: T? ->
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