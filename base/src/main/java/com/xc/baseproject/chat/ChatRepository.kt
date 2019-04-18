package com.xc.baseproject.chat

import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

object ChatRepository {


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
}

data class TestTulingResponse(val code: String, val charge: Boolean, val msg: String, val result: Result)

data class ChatMessage(val message: String, val userName: String, val userAvatar: String)
data class Result(val code: Int, val text: String)