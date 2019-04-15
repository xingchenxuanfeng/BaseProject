package com.xc.baseproject.account.github

import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import retrofit2.http.*


object GithubRepository {
    interface GitHubApi {
        @Headers("Accept: application/json")
        @POST("https://github.com/login/oauth/access_token")
        @FormUrlEncoded
        fun getAccessToken(
                @Field("client_id") clientID: String,
                @Field("client_secret") clientSecret: String,
                @Field("code") code: String
        ): Observable<AccessToken>

        @Headers("Accept: application/vnd.github.v3+json")
        @GET("user")
        fun getUser(@Header("Authorization") authorization: String): Observable<GithubUser>
    }


    val githubApi: GitHubApi by lazy {
        NetService.githubRetrofit.create(GithubRepository.GitHubApi::class.java)
    }
}

data class AccessToken(var access_token: String)
data class GithubUser(
        var avatar_url: String = "",
        var bio: Any = Any(),
        var blog: String = "",
        var company: Any = Any(),
        var created_at: String = "",
        var email: Any = Any(),
        var events_url: String = "",
        var followers: Int = 0,
        var followers_url: String = "",
        var following: Int = 0,
        var following_url: String = "",
        var gists_url: String = "",
        var gravatar_id: String = "",
        var hireable: Any = Any(),
        var html_url: String = "",
        var id: Long = 0,
        var location: Any = Any(),
        var login: String = "",
        var name: String = "",
        var node_id: String = "",
        var organizations_url: String = "",
        var public_gists: Int = 0,
        var public_repos: Int = 0,
        var received_events_url: String = "",
        var repos_url: String = "",
        var site_admin: Boolean = false,
        var starred_url: String = "",
        var subscriptions_url: String = "",
        var type: String = "",
        var updated_at: String = "",
        var url: String = "",
        var authorization: String

)
