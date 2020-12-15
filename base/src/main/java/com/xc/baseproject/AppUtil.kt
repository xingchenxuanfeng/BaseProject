package com.xc.baseproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
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

    val appVersionCode: Int by lazy { BuildConfig.VERSION_CODE }

    val appVersionName: String by lazy { com.blankj.utilcode.util.AppUtils.getAppVersionName() }

    fun jumpToAppMarketDetailPage() {
        val appPkg = appContext.packageName
        val uri = Uri.parse("market://details?id=$appPkg")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appContext.startActivity(intent)
    }
}