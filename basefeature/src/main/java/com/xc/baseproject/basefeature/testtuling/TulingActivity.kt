package com.xc.baseproject.basefeature.testtuling

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xc.baseproject.basefeature.R
import kotlinx.android.synthetic.main.activity_tuling.*
import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.xc.baseproject.BaseActivity
import kotlinx.android.synthetic.main.recycler_view_item_one_line.view.*
import timber.log.Timber

class TulingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuling)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(getContext(), androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)

        val tulingViewModel = ViewModelProviders.of(getContext()).get(TulingViewModel::class.java)
        recyclerView.adapter = TulingAdapter(getContext(), tulingViewModel.mChatData.value)
        tulingViewModel.mChatData.observe(
                getContext(),
                Observer<ArrayList<Pair<Boolean, String>>> {
                    (recyclerView.adapter as TulingAdapter).notifyDataSetChanged()
                }
        )
        sendButton.setOnClickListener {
            val message = editText.text.trim().toString()
            tulingViewModel.sendMessage(message)
            editText.setText("")
            Timber.d(message)
        }
    }
}

class TulingAdapter(var mContext: Context, var mData: ArrayList<Pair<Boolean, String>>?) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_one_line, parent, false)
        return object : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        holder.itemView.textView.autoLinkMask = android.text.util.Linkify.ALL
        mData?.get(position)?.let {
            holder.itemView.textView.text = it.second
            holder.itemView.textView.gravity = if (it.first) Gravity.END else Gravity.START
        }
    }

    override fun getItemCount(): Int = mData?.size ?: 0
}
