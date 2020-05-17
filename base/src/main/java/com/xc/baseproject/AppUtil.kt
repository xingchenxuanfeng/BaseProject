package com.xc.baseproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import com.avos.avoscloud.AVUser
import com.ut.device.UTDevice
import com.xc.baseproject.account.LoginActivity

@SuppressLint("StaticFieldLeak")
object AppUtil {
    lateinit var application: Application
        internal set

    val appContext: Context
        get() = application.applicationContext

    fun getAppChannel() = "main"

    fun getUtDid(): String {
        return UTDevice.getUtdid(appContext)
    }
}