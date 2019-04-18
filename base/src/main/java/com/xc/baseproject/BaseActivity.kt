package com.xc.baseproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.base_activity_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber

@SuppressLint("Registered")
open class BaseActivity : FragmentActivity() {

    fun getContext(): FragmentActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onEventBaseMessage(baseMessage: BaseMessage) {
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.base_activity_layout)
        layoutInflater.inflate(layoutResID, base_activity_container)
        initToolBar()
    }

    override fun setContentView(view: View?) {
        super.setContentView(R.layout.base_activity_layout)
        base_activity_container.addView(view)
        initToolBar()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(R.layout.base_activity_layout)
        if (params !is LinearLayout.LayoutParams
                && params?.javaClass != ViewGroup.LayoutParams::class.java
                && params?.javaClass != ViewGroup.MarginLayoutParams::class.java) {
            Timber.e("params should be LinearLayout.LayoutParams or ")
        }
        base_activity_container.addView(view, params)
        initToolBar()
    }

    private fun initToolBar() {
        setActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Override home navigation button to call onBackPressed
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class BaseMessage(val message: Any)