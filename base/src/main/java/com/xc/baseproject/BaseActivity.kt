package com.xc.baseproject

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity

@SuppressLint("Registered")
open class BaseActivity : FragmentActivity() {

    fun getContext(): FragmentActivity = this

}