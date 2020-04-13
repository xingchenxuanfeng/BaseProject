package com.xc.baseproject.account

import android.os.Bundle
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.blankj.utilcode.util.ToastUtils
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.account.github.GithubAuthListener
import com.xc.baseproject.account.github.GithubOauth
import com.xc.baseproject.account.github.GithubUser
import com.xc.baseproject.constants.GITHUB_CLIENTID
import com.xc.baseproject.constants.GITHUB_CLIENT_SECRET
import com.xc.baseproject.extFunctions.setVisible
import kotlinx.android.synthetic.main.login_acitivity.*
import timber.log.Timber


class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.xc.baseproject.R.layout.login_acitivity)
        login_github.setOnClickListener {
            progress_circular.setVisible(true)
            login_github.isEnabled = false
            GithubOauth(getContext(), GITHUB_CLIENTID, GITHUB_CLIENT_SECRET, null, false).listener(object : GithubAuthListener {
                override fun callback(githubUser: GithubUser?) = if (githubUser != null) {
                    val map: Map<String, Any> = mutableMapOf(
                            "uid" to githubUser.id.toString(),
                            "access_token" to githubUser.authorization
                    )

                    AVUser.loginWithAuthData(map, "github", object : LogInCallback<AVUser>() {
                        override fun done(user: AVUser?, e: AVException?) {
                            progress_circular.setVisible(false)
                            login_github.isEnabled = true
                            if (user != null) {
                                ToastUtils.showShort("登录成功")
                                finish()
                            } else {
                                Timber.e(e)
                                ToastUtils.showShort("登录失败")
                            }
                        }
                    })
                } else {
                    ToastUtils.showShort("登录失败")
                }
            }).authorize()
        }
    }
}