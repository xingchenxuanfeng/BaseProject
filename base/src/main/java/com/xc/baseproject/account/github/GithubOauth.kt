package com.xc.baseproject.account.github

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import com.xc.baseproject.extFunctions.netCompose
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

class GithubOauth(
        private var activity: Activity,
        private var clientId: String,
        private var clientSecret: String,
        private var scopeList: ArrayList<String>? = null,
        private var clearBeforeLaunch: Boolean = false
) {
    private lateinit var listener: GithubAuthListener

    fun listener(listener: GithubAuthListener): GithubOauth {
        this.listener = listener
        return this
    }

    fun authorize() {
        EventBus.getDefault().register(this)
        val scopeList = scopeList
        val githubId = clientId
        val githubSecret = clientSecret

        val intent = Intent(activity, GithubOauthActivity::class.java)
        intent.putExtra("id", githubId)
        intent.putExtra("secret", githubSecret)
        intent.putExtra("clearData", clearBeforeLaunch)
        intent.putStringArrayListExtra("scope_list", scopeList)
        activity.startActivity(intent)
    }

    @SuppressLint("CheckResult")
    @Subscribe
    fun onMessageEvent(event: GitHubAuthEvent) {
        EventBus.getDefault().unregister(this)
        val authToken = event.authToken ?: return listener.callback(null)
        GithubRepository.githubApi.getUser("token $authToken")
                .netCompose()
                .subscribe { user ->
                    user.authorization = authToken
                    listener.callback(user)
                }
    }

}

interface GithubAuthListener {
    fun callback(authToken: GithubUser?)
}

class GitHubAuthEvent(val authToken: String?)
