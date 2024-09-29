package com.onetuchservice.business.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DummyResponseModel() {
    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("next")
    @Expose
    var next: String? = null

    @SerializedName("results")
    @Expose
    var results: ArrayList<ListData>? = null

    inner class ListData() {
        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null
        public override fun toString(): String {
            return ("ListData{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}')
        }
    }
}
