package com.xc

import android.content.Intent
import com.avos.avoscloud.AVUser
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.ToastUtils
import com.xc.baseproject.basefeature.R
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.item_996.view.*

class MainViewHolder : MultiBaseViewHolder<VoteModel>() {
    override fun getLayoutResource(): Int {
        return R.layout.item_996
    }

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: VoteModel) {
        holder.itemView.run {
            rank_tv.text = item.rank.toString()
            name_tv.text = item.name
            vote_count_tv.text = item.voteCount.toString()
            up_ll.isSelected = item.upAction
            down_ll.isSelected = item.downAction
            up_ll.setOnClickListener {
                if (AVUser.getCurrentUser() == null) {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    return@setOnClickListener
                }
                item.upAction = !item.upAction
                item.downAction = false
                adapter.notifyItemChanged(getPosition(holder))
            }
            down_ll.setOnClickListener {
                if (AVUser.getCurrentUser() == null) {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    return@setOnClickListener
                }
                item.downAction = !item.downAction
                item.upAction = false
                adapter.notifyItemChanged(getPosition(holder))
            }
        }
    }
}