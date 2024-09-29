package com.baseapplication.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

object PermissionUtils {
    var REQUEST_ID_MULTIPLE_PERMISSIONS: Int = 1221

    /* Check For Internet Connection Availability */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivity: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivity != null) {
            val info: NetworkInfo? = connectivity.getActiveNetworkInfo()
            if (info != null) {
                return info.getState() == NetworkInfo.State.CONNECTED || info.getState() == NetworkInfo.State.CONNECTING
            }
        }
        return false
    }

    // For Check multiple permissions
    fun checkPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission: String? in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        (permission)!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermissions(activity: Activity, permissions: Array<String?>) {
        if (!checkPermissions(activity.getBaseContext(), *permissions)) {
            val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
            alertBuilder.setCancelable(true)
            alertBuilder.setTitle(("Permission"))
            alertBuilder.setMessage("Permission necessary")
            alertBuilder.setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener({ dialog: DialogInterface?, which: Int ->
                    for (permission: String? in permissions) {
                        try {
//                        Timber.i(permission);
                            if (ActivityCompat.shouldShowRequestPermissionRationale(
                                    activity,
                                    (permission)!!
                                )
                            ) {
                                ActivityCompat.requestPermissions(
                                    activity,
                                    permissions,
                                    REQUEST_ID_MULTIPLE_PERMISSIONS
                                )
                                //                            Timber.i("permission denied, show dialog");
                            } else {
//                            Timber.i("else");
                                ActivityCompat.requestPermissions(
                                    activity,
                                    permissions,
                                    REQUEST_ID_MULTIPLE_PERMISSIONS
                                )
                            }
                        } catch (e: IndexOutOfBoundsException) {
                            e.printStackTrace()
                        }
                    }
                })
            )
            val alert: AlertDialog = alertBuilder.create()
            alert.show()
        }
    }
}