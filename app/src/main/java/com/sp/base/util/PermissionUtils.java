package com.sp.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

public class PermissionUtils {
    public static int REQUEST_ID_MULTIPLE_PERMISSIONS = 1221;

    /* Check For Internet Connection Availability */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                return info.getState() == NetworkInfo.State.CONNECTED || info.getState() == NetworkInfo.State.CONNECTING;
            }
        }
        return false;
    }

    // For Check multiple permissions
    public static boolean checkPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] permissions) {
        if (!checkPermissions(activity.getBaseContext(), permissions)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(("Permission"));
            alertBuilder.setMessage("Permission necessary");
            alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                for (String permission : permissions) {
                    try {
//                        Timber.i(permission);
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                            ActivityCompat.requestPermissions(activity, permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
//                            Timber.i("permission denied, show dialog");
                        } else {
//                            Timber.i("else");
                            ActivityCompat.requestPermissions(activity, permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
                        }

                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }
    }
}