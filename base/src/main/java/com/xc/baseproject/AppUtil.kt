package com.xc.baseproject

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import com.avos.avoscloud.AVUser
import com.xc.baseproject.account.LoginActivity
import com.xc.baseproject.misc.LiveDataToObservableException
import io.reactivex.Observable

@SuppressLint("StaticFieldLeak")
object AppUtil {
    lateinit var appContext: Context
        internal set

    fun tryGetCurrentUser(): AVUser? {
        val currentUser: AVUser? = AVUser.getCurrentUser()
        if (currentUser == null) {
            appContext.startActivity(Intent(appContext, LoginActivity::class.java))
            return null
        }
        return currentUser

    }

}