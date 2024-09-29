package com.baseapplication.util

import android.util.Log

object LogUtil {
    var isEnableLogs: Boolean = true
    fun printLog(tag: String?, value: String?) {
        if (isEnableLogs && value != null) {
            Log.e(tag, "" + value)
        }
    }
}
