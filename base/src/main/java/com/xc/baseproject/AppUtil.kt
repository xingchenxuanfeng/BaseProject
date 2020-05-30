package com.xc.baseproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import com.meituan.android.walle.WalleChannelReader
import com.ut.device.UTDevice
import com.xc.baseproject.account.LoginActivity

@SuppressLint("StaticFieldLeak")
object AppUtil {
    lateinit var application: Application
        internal set

    val appContext: Context
        get() = application.applicationContext

    val appChannel: String by lazy { WalleChannelReader.getChannel(appContext) ?: "origin" }

    fun getUtDid(): String {
        return UTDevice.getUtdid(appContext)
    }
}