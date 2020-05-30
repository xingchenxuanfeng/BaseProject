package com.xc.baseproject.chat

import androidx.annotation.Keep
import com.avos.avoscloud.*
import com.xc.baseproject.account.AccountManager
import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable
import java.util.*

object ChatRepository {
    init {
        AVObject.registerSubclass(ChatMessage::class.java)
    }

    interface Api {
        @GET("turing/turing")
        fun getChatResponse(
                @Query("info") info: String?,
                @Query("loc") loc: String = "",
                @Query("userid") userid: String = UUID.randomUUID().toString(),
                @Query("appkey") appkey: String = NetService.wayJdApiAppKey
        ): Observable<TestTulingResponse>
    }

    private val getChatApi by lazy {
        NetService.wayJdRetrofit.create(Api::class.java)
    }

    fun getChatResponse(info: String?,
                        loc: String = "",
                        userid: String = UUID.randomUUID().toString(),
                        appkey: String = NetService.wayJdApiAppKey): Observable<TestTulingResponse> {
        return getChatApi.getChatResponse(info, loc, userid, appkey)
    }

    fun postMessage(message: String): Observable<ChatMessage> {
        return Observable.create(object : ObservableOnSubscribe<ChatMessage> {
            override fun subscribe(emitter: ObservableEmitter<ChatMessage>) {
                val currentUser = AccountManager.tryGetCurrentUser() ?: return

                val chatMessage = ChatMessage()
                chatMessage.message = message
                chatMessage.user = currentUser

                chatMessage.saveInBackground(object : SaveCallback() {
                    override fun done(e: AVException?) {
                        emitter.onNext(chatMessage)
                        emitter.onComplete()
                    }
                })
            }
        })

    }
}

@Keep
data class TestTulingResponse(val code: String, val charge: Boolean, val msg: String, val result: Result)

@Keep
data class Result(val code: Int, val text: String)

@AVClassName("ChatMessage")
class ChatMessage : Serializable, AVObject() {
    var message: String
        get() = getString("message") ?: "message"
        set(value) = put("message", value)
    var user: AVUser
        get() = getAVObject("user", AVUser::class.java)
        set(value) = put("user", value)
}
