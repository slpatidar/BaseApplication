package com.onetuchservice.business.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DummyResponse {
    @SerializedName("data")
    @Expose
    var results: ArrayList<ListData>? = null
}
