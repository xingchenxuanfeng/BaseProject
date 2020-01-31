package com.xc.baseproject.hotpatch

import android.annotation.SuppressLint
import com.blankj.utilcode.util.ThreadUtils
import com.taobao.sophix.SophixManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object HotpatchManager {

    @SuppressLint("CheckResult")
    fun queryAndLoadNewPatch() {
        Observable.timer(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    SophixManager.getInstance().queryAndLoadNewPatch()
                }
    }
}