package com.baseapplication.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.location.Location
import android.os.Looper
import com.baseapplication.BaseApplication
import com.baseapplication.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStates
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiPredicate
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

/**
 * Created by Pepe on 01/25/19.
 */
class LocationProvider() {
    protected val locationClient: FusedLocationProviderClient
    protected val locationUpdatesRequest: LocationRequest
    protected val settingsClient: SettingsClient
    protected val disposableList: DisposableList = DisposableList()
    protected var locationCallback: LocationCallback? = null
    protected fun context(): Context {
        return BaseApplication.instance?.context!!
    }

    protected val ENABLE_LOCATION_SERVICES_REQUEST: Int = 32

    init {
        locationClient = LocationServices.getFusedLocationProviderClient(context())
        locationUpdatesRequest = LocationRequest()
        locationUpdatesRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        settingsClient = LocationServices.getSettingsClient(context())
    }

    fun requestEnableLocationServices(activity: Activity?): Completable {
        return Completable.create(CompletableOnSubscribe({ e: CompletableEmitter ->
            val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(locationUpdatesRequest)
            settingsClient.checkLocationSettings(builder.build())
                .addOnCompleteListener(OnCompleteListener<LocationSettingsResponse?>({ task: Task<LocationSettingsResponse?> ->
                    try {
                        val response: LocationSettingsResponse? =
                            task.getResult(ApiException::class.java)
                        if (response == null) {
                            e.onError(Error(context().getString(R.string.location_services_problem_message)))
                            return@OnCompleteListener
                        }
                        val states: LocationSettingsStates? = response.getLocationSettingsStates()
                        if (states == null) {
                            e.onError(Error(context().getString(R.string.location_services_problem_message)))
                            return@OnCompleteListener
                        }
                        if (states.isLocationUsable()) {
                            e.onComplete()
                        } else {
                            e.onError(Error(context().getString(R.string.location_services_not_enabled)))
                        }
                    } catch (exception: ApiException) {
                        if (exception.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            try {
                                val apiException: ResolvableApiException =
                                    exception as ResolvableApiException
                                apiException.startResolutionForResult(
                                    (activity)!!,
                                    ENABLE_LOCATION_SERVICES_REQUEST
                                )
                                disposableList.add(
                                    ActivityResultPushSubjectHolder.shared()
                                    .filter(Predicate({ activityResult: ActivityResult? -> activityResult!!.requestCode == ENABLE_LOCATION_SERVICES_REQUEST }))
                                    .subscribe(Consumer({ activityResult: ActivityResult? ->
                                        if (activityResult!!.resultCode == Activity.RESULT_OK) {
                                            e.onComplete()
                                        } else {
                                            e.onError(Error(context().getString(R.string.location_services_not_enabled)))
                                        }
                                    }))
                                )
                            } catch (exeption: SendIntentException) {
                                e.onError(exception)
                            }
                        } else {
                            e.onError(exception)
                        }
                    }
                }))
        }))
    }

    fun requestLocationUpdates(
        activity: Activity,
        interval: Long,
        distance: Int
    ): Observable<Location> {
        return requestLocationUpdates(activity, interval)
            .distinctUntilChanged(BiPredicate({ l1: Location, l2: Location? ->
                l1.distanceTo(
                    (l2)!!
                ) < distance
            }))
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(activity: Activity, interval: Long): Observable<Location> {
        return requestEnableLocationServices(activity)
            .andThen(
                PermissionRequestHandler.instance.requestLocationAccess(activity)
            )
            .andThen<Location>(
                Observable.create<Location>(
                    ObservableOnSubscribe<Location>({ observable: ObservableEmitter<Location> ->
                        locationUpdatesRequest.setInterval(interval * 1000)
                        locationUpdatesRequest.setFastestInterval(interval * 1000)
                        if (locationCallback != null) {
                            locationClient.removeLocationUpdates(locationCallback!!)
                        }
                        locationCallback = object : LocationCallback() {
                            public override fun onLocationResult(locationResult: LocationResult) {
                                val location: Location? =
                                    getMostAccurateLocation(locationResult.getLocations())
                                if (location != null) {
                                    observable.onNext(location)
                                }
                            }
                        }
                        activity.runOnUiThread(Runnable({
                            locationClient.requestLocationUpdates(
                                locationUpdatesRequest,
                                locationCallback as LocationCallback,
                                Looper.myLooper()
                            )
                        }))
                    }) as ObservableOnSubscribe<Location>?
                ).subscribeOn(Schedulers.single()).observeOn(
                    AndroidSchedulers.mainThread()
                )
            )
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(activity: Activity): Single<Location?> {
        return PermissionRequestHandler.instance.requestLocationAccess(activity)
            .andThen<Location?>(
                (Single.create<Location?>(
                    SingleOnSubscribe<Location?>({ single: SingleEmitter<Location?> ->
                        locationClient.getLastLocation().addOnSuccessListener(
                            OnSuccessListener({ location: Location? ->
                                if (location != null) {
                                    single.onSuccess(location)
                                } else {
                                    single.onError(
                                        Error(
                                            context().getResources()
                                                .getString(R.string.location_is_null)
                                        )
                                    )
                                }
                            })
                        ).addOnFailureListener(OnFailureListener({ t: Exception? ->
                            single.onError(
                                (t)!!
                            )
                        }))
                    }) as SingleOnSubscribe<Location?>?
                )).subscribeOn(
                    Schedulers.single()
                )
            )
    }

    fun getMostAccurateLocation(locations: List<Location?>): Location? {
        var accurateLocation: Location? = null
        for (location: Location? in locations) {
            if (location == null) continue
            if (accurateLocation == null || location.getAccuracy() >= accurateLocation.getAccuracy()) {
                accurateLocation = location
            }
        }
        return accurateLocation
    }

    fun dispose() {
        disposableList.dispose()
    }
}
