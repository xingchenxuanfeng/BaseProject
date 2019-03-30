package com.xc

import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import retrofit2.http.GET
import java.io.Serializable

object Repository {
    interface GithubApi {
        @GET("master/testStaticData")
        fun getTestData(): Observable<TestData>
    }

    val githubApi: GithubApi by lazy {
        NetService.githubRetrofit.create(GithubApi::class.java)
    }
}

data class TestData(val data: String) : Serializable