package com.xc

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
            up_ll.isSelected = (item.voteAction == true)
            down_ll.isSelected = (item.voteAction == false)
            up_ll.setOnClickListener {
                ToastUtils.showShort("顶")
                item.voteAction = if (item.voteAction != true) true else null
                adapter.notifyItemChanged(getPosition(holder))
            }
            down_ll.setOnClickListener {
                ToastUtils.showShort("踩")
                item.voteAction = if (item.voteAction != false) false else null
                adapter.notifyItemChanged(getPosition(holder))
            }
        }
    }
}