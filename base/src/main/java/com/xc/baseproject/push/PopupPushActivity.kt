package com.xc.baseproject.push


import android.os.Bundle
import com.alibaba.sdk.android.push.AndroidPopupActivity
import com.xc.baseproject.R
import timber.log.Timber

class PopupPushActivity : AndroidPopupActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_aliyun)
    }

    /**
     * 实现通知打开回调方法，获取通知相关信息
     * @param title     标题
     * @param content   内容
     * @param extMap    额外参数
     */
    override fun onSysNoticeOpened(title: String?, body: String?, extMap: Map<String, String>?) {
        val content = extMap?.get("content")
        Timber.d("onSysNoticeOpened, title: $title, body: $body, extMap: $extMap")


    }

    companion object {
        const val TAG = "PopupPushActivity"
    }
}