package com.xc.baseproject.chat

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.avos.avoscloud.AVUser
import com.xc.baseproject.AppUtil
import com.xc.baseproject.extFunctions.netCompose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import kotlin.random.Random

class ChatViewModel : ViewModel() {
    private val mDisposable = CompositeDisposable()
    /**
     * Map <send -> true | receive -> false,message>
     */
    val mChatData = MutableLiveData<MutableList<ChatMessage>>()

    init {
        mChatData.value = mutableListOf()
    }

    @SuppressLint("CheckResult")
    fun sendMessage(message: String) {
        val chatData = mChatData.value
        ChatRepository.postMessage(message).subscribe {
            chatData?.add(it)
            mChatData.value = chatData
        }
//        val currentUser = AppUtil.tryGetCurrentUser() ?: return
//
//        val chatData = mChatData.value
//        val disposable = ChatRepository.getChatResponse(message).netCompose()
//                .doOnSubscribe {
//
//                    val chatMessage = ChatMessage()
//                    chatMessage.message = message
//                    chatMessage.user = currentUser
//                    chatData?.add(chatMessage)
//                    mChatData.value = chatData
//                }
//                .subscribe { result ->
//                    if (result == null) return@subscribe
//
//                    val chatMessage = ChatMessage()
//                    chatMessage.message = result.result.text
//                    chatMessage.user = currentUser
//                    chatData?.add(chatMessage)
//
//                    mChatData.value = chatData
//                }
//        mDisposable.add(disposable)
    }

    fun fetch() {

    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.dispose()
    }
}