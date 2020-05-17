package com.xc.baseproject.account

import android.content.Intent
import com.avos.avoscloud.AVUser
import com.xc.baseproject.AppUtil


object AccountManager {

    fun tryGetCurrentUser(): AVUser? {
        val currentUser: AVUser? = AVUser.getCurrentUser()
        if (currentUser == null) {
            val intent: Intent = Intent(AppUtil.appContext,
                    LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            AppUtil.appContext.startActivity(intent)
            return null
        }
        return currentUser

    }

}