package com.xc.baseproject.basefeature.testtuling

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TulingViewModel : ViewModel() {
    private val mDisposable = CompositeDisposable()
    /**
     * Map <send -> true | receive -> false,message>
     */
    val mChatData = MutableLiveData<ArrayList<Pair<Boolean, String>>>()

    init {
        mChatData.value = ArrayList()
    }

    fun sendMessage(message: String) {
        val chatData = mChatData.value
        val disposable = TulingRepository.getTulingResponse(message).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    chatData?.add(Pair(true, message))
                    mChatData.value = chatData
                }
                .subscribe(
                        { result ->
                            chatData?.add(Pair(false, result?.result?.text ?: "something wrong"))
                            mChatData.value = chatData
                        },
                        { ex -> Timber.e(ex) }
                )
        mDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.dispose()
    }
}