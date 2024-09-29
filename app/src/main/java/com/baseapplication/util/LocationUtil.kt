package com.baseapplication.util

import android.content.Context
import android.location.Location
import java.text.DecimalFormat

object LocationUtil {
    // For getting distance between two locations
    fun getDistance(
        context: Context?,
        sLatitude: String,
        sLongitude: String,
        dLatitude: String,
        dLongitude: String
    ): String {
        if (!(dLatitude == "") && !(dLongitude == "")) {
            var mLatitude: Double = 0.0
            var mLongitude: Double = 0.0
            try {
                mLatitude = dLatitude.toDouble()
                mLongitude = dLongitude.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            val locationA: Location = Location("point A")
            if (!(sLongitude == "")) {
                try {
                    locationA.setLatitude(sLatitude.toDouble())
                    locationA.setLongitude(sLongitude.toDouble())
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            }
            val locationB: Location = Location("point B")
            locationB.setLatitude(mLatitude)
            locationB.setLongitude(mLongitude)
            val distance: Double = (locationA.distanceTo(locationB) / 1609).toDouble()
            val mDistanceStr: String = DecimalFormat("###.##").format(distance) + " mi"
            return mDistanceStr.replace(",", ".")
        }
        return ""
    }
}
