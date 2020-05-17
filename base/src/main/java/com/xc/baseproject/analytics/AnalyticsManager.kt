package com.xc.baseproject.analytics

import com.alibaba.sdk.android.man.MANService
import com.alibaba.sdk.android.man.MANServiceProvider
import com.xc.baseproject.AppUtil
import com.xc.baseproject.BuildConfig
import com.xc.baseproject.aliyunAppKey
import com.xc.baseproject.aliyunAppSecret

object AnalyticsManager {
    fun init() {
        // 获取MAN服务
        // 【注意】建议您在Application中初始化MAN，以保证正常获取MANService
        val manService: MANService = MANServiceProvider.getService()

        // 若需要关闭 SDK 的自动异常捕获功能可进行如下操作(如需关闭crash report，建议在init方法调用前关闭crash),详见文档5.4
        // 打开调试日志，线上版本建议关闭
        if (BuildConfig.DEBUG) {
            manService.manAnalytics.turnOnDebug()
        }
        // 若需要关闭 SDK 的自动异常捕获功能可进行如下操作(如需关闭crash report，建议在init方法调用前关闭crash),详见文档5.4
        manService.manAnalytics.turnOffCrashReporter()

        // MAN初始化
        manService.manAnalytics.init(AppUtil.application, AppUtil.appContext, aliyunAppKey, aliyunAppSecret)

        // 通过此接口关闭页面自动打点功能，详见文档4.2
        // manService.manAnalytics.turnOffAutoPageTrack()

        manService.manAnalytics.setAppVersion(BuildConfig.VERSION_CODE.toString())

        manService.manAnalytics.setChannel(AppUtil.getAppChannel())
    }
}