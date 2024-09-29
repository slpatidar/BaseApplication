package com.baseapplication.location

import com.google.android.gms.maps.model.LatLng

object GoogleUtils {
    fun getMapImageURL(location: LatLng, width: Int, height: Int): String {
        val googleMapsAPIKey: String = "enter your google map api key"
        val api: String = "https://maps.googleapis.com/maps/api/staticmap"
        val markers: String = "markers=" + location.latitude + "," + location.longitude
        val size: String = "zoom=18&size=" + width + "x" + height
        val key: String = "key=" + googleMapsAPIKey
        return api + "?" + markers + "&" + size + "&" + key
    }

    fun getMapWebURL(location: LatLng): String {
        return "http://maps.google.com/maps?z=12&t=m&q=loc:" + location.latitude + "+" + location.longitude
    }
}
