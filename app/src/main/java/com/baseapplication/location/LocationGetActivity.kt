package com.baseapplication.location

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.baseapplication.R
import com.google.android.gms.maps.model.LatLng
import io.reactivex.functions.Function

class LocationGetActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_get)
        val locationProvider: LocationProvider = LocationProvider()
        locationProvider.requestEnableLocationServices(this)
        locationProvider.requestLocationUpdates(this, 1, 2)
        locationProvider.getLastLocation(this).map(Function({ location: Location? ->
            val latLng: LatLng = LatLng(
                location!!.getLatitude(), location.getLongitude()
            )

//            val width = getResources().getDimensionPixelSize(R.dimen.message_image_max_width);
//            val height = getResources().getDimensionPixelSize(R.dimen.message_image_max_height);

//            return  Result(latLng, GoogleUtils.getMapImageURL(latLng, width, height));
            Log.e("Sp", "" + latLng.latitude + " " + latLng.longitude)
            ""
        }))
    }
}