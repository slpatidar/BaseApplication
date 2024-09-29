package com.baseapplication.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPref class is used for saving data into sharedPreference
 */
class SharedPref private constructor(private val mContext: Context) {
    init {
        mSharedPref =
            mContext.getSharedPreferences(mContext.getPackageName(), Activity.MODE_PRIVATE)
    }

    fun read(key: String?, defValue: String?): String? {
        return mSharedPref.getString(key, defValue)
    }

    fun read(key: String?, defValue: Int): Int {
        return mSharedPref.getInt(key, defValue)
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return mSharedPref.getBoolean(key, defValue)
    }

    fun write(key: String?, value: String?) {
        try {
            val prefsEditor: SharedPreferences.Editor = mSharedPref.edit()
            prefsEditor.putString(key, value)
            prefsEditor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun write(key: String?, value: Int) {
        try {
            val prefsEditor: SharedPreferences.Editor = mSharedPref.edit()
            prefsEditor.putInt(key, value)
            prefsEditor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun write(key: String?, value: Boolean) {
        try {
            val prefsEditor: SharedPreferences.Editor = mSharedPref.edit()
            prefsEditor.putBoolean(key, value)
            prefsEditor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private var mInstance: SharedPref? = null
        private lateinit var mSharedPref: SharedPreferences

        // use getApplicationContext() to call this method
        fun getInstance(context: Context): SharedPref? {
            if (mInstance == null) {
                mInstance = SharedPref(context)
            }
            return mInstance
        }
    }
}
