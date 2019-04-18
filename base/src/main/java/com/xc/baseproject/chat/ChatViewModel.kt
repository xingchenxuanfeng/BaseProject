package com.xc.baseproject.chat

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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

    fun sendMessage(message: String) {
        val chatData = mChatData.value
        val disposable = ChatRepository.getChatResponse(message).netCompose()
                .doOnSubscribe {
                    chatData?.add(ChatMessage(message, "user" + (1..100).random().toString(), "https://avatars2.githubusercontent.com/u/7694736?v=4"))
                    mChatData.value = chatData
                }
                .subscribe { result ->
                    if (result == null) return@subscribe
                    chatData?.add(ChatMessage(result.result.text, "user" + (1..100).random().toString(), "https://avatars2.githubusercontent.com/u/7694736?v=4"))
                    mChatData.value = chatData
                }
        mDisposable.add(disposable)
    }

    fun fetch() {

    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.dispose()
    }
}