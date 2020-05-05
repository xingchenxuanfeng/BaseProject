package com.xc.baseproject.extFunctions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import timber.log.Timber

fun Number?.toStringWithSign(): String? {
    return when {
        this == null -> null
        this.toDouble() < 0 -> this.toString()
        this.toDouble() > 0 -> "+$this"
        else -> "相等"
    }
}
