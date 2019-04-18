package com.xc

import android.content.Intent
import com.avos.avoscloud.AVUser
import com.xc.VoteModel.Companion.VOTE_STATE_DOWN
import com.xc.VoteModel.Companion.VOTE_STATE_NONE
import com.xc.VoteModel.Companion.VOTE_STATE_UP
import com.xc.baseproject.account.LoginActivity
import com.xc.baseproject.basefeature.R
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.item_996.view.*
import org.greenrobot.eventbus.EventBus

class MainViewHolder : MultiBaseViewHolder<VoteModel>() {
    override fun getLayoutResource(): Int {
        return R.layout.item_996
    }

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: VoteModel) {
        holder.itemView.run {
            rank_tv.text = item.rank.toString()
            name_tv.text = item.name
            vote_count_tv.text = (item.voteCount + item.remoteVoteCount).toString()
            val userCurrentVote = Repository.getCurrentVote()
            up_ll.isSelected = (item == userCurrentVote && userCurrentVote.voteState == VOTE_STATE_UP)
            down_ll.isSelected = (item == userCurrentVote && userCurrentVote.voteState == VOTE_STATE_DOWN)
            up_ll.setOnClickListener {
                if (AVUser.getCurrentUser() == null) {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    return@setOnClickListener
                }
                val newVoteState = if (item.voteState == VOTE_STATE_UP) VOTE_STATE_NONE else VOTE_STATE_UP

                Repository.modifyMyVote(item, newVoteState)
                adapter.notifyItemChanged(getPosition(holder))
            }
            down_ll.setOnClickListener {
                if (AVUser.getCurrentUser() == null) {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    return@setOnClickListener
                }

                val newVoteState = if (item.voteState == VOTE_STATE_DOWN) VOTE_STATE_NONE else VOTE_STATE_DOWN

                Repository.modifyMyVote(item, newVoteState)
                adapter.notifyItemChanged(getPosition(holder))
            }
        }
    }
}