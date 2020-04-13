package com.xc.baseproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import com.avos.avoscloud.AVUser
import com.xc.baseproject.account.LoginActivity

@SuppressLint("StaticFieldLeak")
object AppUtil {
    lateinit var application: Application
        internal set

    val appContext: Context
        get() = application.applicationContext

    fun tryGetCurrentUser(): AVUser? {
        val currentUser: AVUser? = AVUser.getCurrentUser()
        if (currentUser == null) {
            val intent: Intent = Intent(appContext,
                    LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            appContext.startActivity(intent)
            return null
        }
        return currentUser

    }

}