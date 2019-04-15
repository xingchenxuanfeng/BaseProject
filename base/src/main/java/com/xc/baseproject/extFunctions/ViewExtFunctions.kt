package com.xc.baseproject.extFunctions

import android.view.View

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