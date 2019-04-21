package com.xc.baseproject.extFunctions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import timber.log.Timber

/**
 * @author xc
 * @time 19-4-15.
 */

fun View?.setVisible(visible: Boolean) {
    if (this == null) {
        return
    }
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun ImageView?.loadUrl(imageUrl: String?) {
    if (this == null) return Timber.e(imageUrl)
    Glide.with(this).load(imageUrl).into(this)
}