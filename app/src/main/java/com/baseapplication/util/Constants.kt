package com.baseapplication.util

import android.os.Build

open interface Constants {
    companion object {
        val ANDROID: String = "Android"
        val DEVICE_OS_VALUE: String = Build.VERSION.RELEASE
        val TABLET: String = "Tablet"
        val PHONE: String = "Phone"
        val INTERNAL_FOLDER_NAME: String = Build.ID
        val DEFAULT_IMAGE_SIZE: Int = 240
    }
}
