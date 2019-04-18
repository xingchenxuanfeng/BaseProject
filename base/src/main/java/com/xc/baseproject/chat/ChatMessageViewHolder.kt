package com.xc.baseproject.chat

import com.bumptech.glide.Glide
import com.xc.baseproject.R
import com.xc.baseproject.extFunctions.loadUrl
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.chat_item.view.*

/**
 * @author xc
 * @time 19-4-18.
 */
class ChatMessageViewHolder : MultiBaseViewHolder<ChatMessage>() {
    override fun getLayoutResource(): Int = R.layout.chat_item

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: ChatMessage) {
        holder.itemView.run {
            avatars.loadUrl(item.userAvatar)
            user_name.text = item.userName
            chat_message.setFullString(item.message)

        }
    }
}