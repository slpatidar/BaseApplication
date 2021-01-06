package com.sp.base.util;

import android.util.Log;

public class LogUtil {

    public static boolean isEnableLogs = true;

    public static void printLog(String tag, String value) {
        if (isEnableLogs && value != null) {
            Log.e(tag, "" + value);
        }
    }

}
