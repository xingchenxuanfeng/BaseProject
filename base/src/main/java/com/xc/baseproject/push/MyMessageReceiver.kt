package com.xc.baseproject.push

import android.content.Context
import android.util.Log
import com.alibaba.sdk.android.push.MessageReceiver
import com.alibaba.sdk.android.push.notification.CPushMessage
import timber.log.Timber

class MyMessageReceiver : MessageReceiver() {
    public override fun onNotification(context: Context, title: String, summary: String, extraMap: Map<String, String>) {
        // TODO 处理推送通知
        Timber.d("Receive notification, title: $title, summary: $summary, extraMap: $extraMap")
    }

    public override fun onMessage(context: Context, cPushMessage: CPushMessage) {
        Timber.d("onMessage, messageId: ${cPushMessage.messageId}, title: ${cPushMessage.title}, content:${cPushMessage.content}")
    }

    public override fun onNotificationOpened(context: Context, title: String, summary: String, extraMap: String) {
        Timber.d("onNotificationOpened, title: $title, summary: $summary, extraMap:$extraMap")
    }

    override fun onNotificationClickedWithNoAction(context: Context, title: String, summary: String, extraMap: String) {
        Timber.d("onNotificationClickedWithNoAction, title: $title, summary: $summary, extraMap:$extraMap")
    }

    override fun onNotificationReceivedInApp(context: Context, title: String, summary: String, extraMap: Map<String, String>, openType: Int, openActivity: String, openUrl: String) {
        Timber.d("onNotificationReceivedInApp, title: $title, summary: $summary, extraMap:$extraMap, openType:$openType, openActivity:$openActivity, openUrl:$openUrl")
    }

    override fun onNotificationRemoved(context: Context, messageId: String) {
        Timber.d("onNotificationRemoved")
    }

}