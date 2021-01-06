package com.sp.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPref class is used for saving data into sharedPreference
 */
public class SharedPref {
    private static SharedPref mInstance = null;
    private static SharedPreferences mSharedPref;
    private Context mContext;

    // use getApplicationContext() to call this method
    public static SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    private SharedPref(Context context) {
        mContext = context;
        mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public int read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public void write(String key, String value) {
        try {
            SharedPreferences.Editor prefsEditor = mSharedPref.edit();
            prefsEditor.putString(key, value);
            prefsEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String key, int value) {
        try {
            SharedPreferences.Editor prefsEditor = mSharedPref.edit();
            prefsEditor.putInt(key, value);
            prefsEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String key, boolean value) {
        try {
            SharedPreferences.Editor prefsEditor = mSharedPref.edit();
            prefsEditor.putBoolean(key, value);
            prefsEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
