package com.xc.baseproject.account.github

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xc.baseproject.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.util.*

class GithubOauthActivity : BaseActivity() {

    companion object {
        const val GITHUB_URL = "https://github.com/login/oauth/authorize"
        const val GITHUB_OAUTH = "https://github.com/login/oauth/access_token"
    }

    private lateinit var webView: WebView

    var clientId: String? = null
    var clientSecret: String? = null

    private var clearDataBeforeLaunch = false
    private var debug = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        var scopeAppendToUrl = ""

        val intent = intent
        if (intent.extras != null) {
            clientId = intent.getStringExtra("id")
            clientSecret = intent.getStringExtra("secret")
            clearDataBeforeLaunch = intent.getBooleanExtra("clearData", false)
        } else {
            Timber.d("intent extras null")
            EventBus.getDefault().post(GitHubAuthEvent(null))
            finish()
        }

        var urlLoad = "$GITHUB_URL?client_id=$clientId"

        val scopeList: ArrayList<String>? = intent.getStringArrayListExtra("scope_list")
        if (!scopeList.isNullOrEmpty()) {
            scopeAppendToUrl = getScopeUrl(scopeList)
            urlLoad += "&scope=$scopeAppendToUrl"
        }
        if (debug) {
            Timber.d("intent received is \n client id: $clientId\n secret:$clientSecret\n Scope request are : $scopeAppendToUrl")
        }

        if (clearDataBeforeLaunch) clearDataBeforeLaunch()

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                super.shouldOverrideUrlLoading(view, url)
                try {
                    if (!url.contains("?code=")) return false
                    finish()
                    val code = url.substring(url.lastIndexOf("?code=") + 1)
                    val token = code.split("=")[1].split("&")[0]

                    fetchOauthTokenWithCode(token)

                    if (debug) {
                        Timber.d("code fetched is: $code")
                        Timber.d("token cleaned is: $token")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            }
        }

        webView.loadUrl(urlLoad)
    }

    private fun clearDataBeforeLaunch() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies { aBoolean ->
            // a callback which is executed when the cookies have been removed
            Timber.d("Cookie removed: $aBoolean")
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchOauthTokenWithCode(code: String) {
        GithubRepository.githubApi.getAccessToken(clientId!!, clientSecret!!, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val authToken = it.access_token
                    storeToSharedPreference(authToken)
                    EventBus.getDefault().post(GitHubAuthEvent(authToken))
                }, {
                    storeToSharedPreference(null)
                    EventBus.getDefault().post(GitHubAuthEvent(null))
                })
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun storeToSharedPreference(auth_token: String?) {
        val prefs = getSharedPreferences("github_prefs", Context.MODE_PRIVATE)
        val edit = prefs.edit()

        edit.putString("oauth_token", auth_token)
        edit.apply()
    }

    private fun getScopeUrl(scopeList: List<String>): String = scopeList.joinToString()
}
