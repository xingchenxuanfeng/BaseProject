package com.xc.baseproject.net

import com.google.gson.GsonBuilder
import com.xc.baseproject.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetService {
    private val baseOkHttpClient: OkHttpClient
    private val baseRetrofit: Retrofit
    val wayJdRetrofit: Retrofit
    var githubRetrofit: Retrofit

    const val wayJdApiAppKey = "e4388cc76bf9529e6e29a938cbdf839d"
    private const val baseUrl = "https://localhost/"

    private const val baseWayJdApiUrl = "https://way.jd.com/"
    private const val baseGithubApiUrl = "https://api.github.com/"

    init {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
        baseOkHttpClient = okHttpBuilder.build()

        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl(baseUrl)
        retrofitBuilder.client(baseOkHttpClient)
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        baseRetrofit = retrofitBuilder.build()

        wayJdRetrofit = baseRetrofit.newBuilder()
                .baseUrl(baseWayJdApiUrl)
                .build()
        githubRetrofit = baseRetrofit.newBuilder()
                .baseUrl(baseGithubApiUrl)
                .build()
    }

}