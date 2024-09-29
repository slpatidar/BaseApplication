package com.baseapplication.ui.adapter

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AllNotificationData() {
    @SerializedName("firstName")
    @Expose
    var firstName: String? = null
}
