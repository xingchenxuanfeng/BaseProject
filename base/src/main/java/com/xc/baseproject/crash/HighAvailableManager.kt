package com.xc.baseproject.crash

import com.alibaba.ha.adapter.AliHaAdapter
import com.alibaba.ha.adapter.AliHaConfig
import com.alibaba.ha.adapter.Plugin
import com.alibaba.ha.adapter.service.tlog.TLogLevel
import com.alibaba.ha.adapter.service.tlog.TLogService
import com.blankj.utilcode.util.ProcessUtils
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.xc.baseproject.AppUtil
import com.xc.baseproject.BuildConfig
import com.xc.baseproject.aliyunAppKey
import com.xc.baseproject.aliyunAppSecret


object HighAvailableManager {

    fun init() {
        initAliHA()

        initFirebaseCrashlytics()
    }

    private fun initAliHA() {
        TLogService.updateLogLevel(TLogLevel.VERBOSE);

        val config = AliHaConfig()
        config.appKey = aliyunAppKey //appkey
        config.appVersion = BuildConfig.VERSION_CODE.toString() //应用的版本号
        config.appSecret = aliyunAppSecret //appsecret
        config.channel = AppUtil.getAppChannel() //应用的渠道号标记，自定义
        config.userNick = null
        config.application = AppUtil.application
        config.context = AppUtil.appContext
        config.isAliyunos = false //是否为yunos
        //启动CrashReporter
        AliHaAdapter.getInstance().addPlugin(Plugin.crashreporter)
        AliHaAdapter.getInstance().addPlugin(Plugin.apm)
        AliHaAdapter.getInstance().addPlugin(Plugin.tlog)
        //        AliHaAdapter.getInstance().openDebug(BuildConfig.DEBUG)

        AliHaAdapter.getInstance().start(config)
    }

    private fun initFirebaseCrashlytics() {
        if (ProcessUtils.isMainProcess()) {//非主进程调用会抛异常
            FirebaseCrashlytics.getInstance().setUserId(AppUtil.getUtDid())
        }
    }

}