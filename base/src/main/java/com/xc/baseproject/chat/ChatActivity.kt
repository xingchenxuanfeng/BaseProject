package com.xc.baseproject.chat

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.R
import com.xc.baseproject.extFunctions.addAllIfNotNull
import kotlinx.android.synthetic.main.activity_chat.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import timber.log.Timber

class ChatActivity : BaseActivity() {
    companion object {
        const val LATEST_ON_TOP = "latestOnTop"
        const val LABEL = "label"
        const val TIP = "tip"
        const val COMPANY_ID = "company_id"
    }

    private var label: CharSequence = "聊天"
    private var latestOnTop: Boolean = true
    private var tip: String = ""
    private var companyId: String = ""

    private var data: MutableList<ChatMessage> = mutableListOf()
    private var adapter: MultiTypeAdapter = MultiTypeAdapter(data)
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initData()
        initView()
        initListener()

    }

    private fun initView() {
        title = label
        tip_tv.text = tip
        sp_layout.setColorSchemeColors(Color.rgb(47, 223, 189))
        val linearLayoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, latestOnTop)
        linearLayoutManager.stackFromEnd = latestOnTop
        recyclerView.layoutManager = linearLayoutManager

        adapter.register(ChatMessage::class, ChatMessageViewHolder())
        recyclerView.adapter = adapter

    }

    private fun initData() {
        chatViewModel = ViewModelProviders.of(getContext()).get(ChatViewModel::class.java)
        chatViewModel.mChatData.observe(
                getContext(),
                Observer<MutableList<ChatMessage>> {
                    data.clear()
                    data.addAllIfNotNull(it)
                    recyclerView.smoothScrollToPosition(Math.max(0, adapter.itemCount - 1))
                    recyclerView.adapter?.notifyDataSetChanged()
                }
        )
        label = intent.getStringExtra(LABEL) ?: label
        latestOnTop = intent.getBooleanExtra(LATEST_ON_TOP, latestOnTop)
        tip = intent.getStringExtra(TIP) ?: tip
        companyId = intent.getStringExtra(COMPANY_ID) ?: companyId
    }

    private fun initListener() {
        sp_layout.setOnRefreshListener {
            chatViewModel.fetch()
            sp_layout.isRefreshing = false
        }

        sendButton.setOnClickListener {
            val message = editText.text.trim().toString()
            chatViewModel.sendMessage(message)
            editText.setText("")
            Timber.d(message)
        }
    }
}
