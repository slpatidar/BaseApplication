package com.onetuchservice.business.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListData {
    @SerializedName("firstName")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}
