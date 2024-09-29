package com.baseapplication.countrypicker

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.util.Scanner

object CountryPickerUtils {
    fun getMipmapResId(context: Context, drawableName: String): Int {
        return context.getResources().getIdentifier(
            drawableName.lowercase(), "mipmap", context.getPackageName()
        )
    }

    fun getCountriesJSON(context: Context): JSONObject? {
        val resourceName: String = "countries_dialing_code_ke_in"
        val resourceId: Int = context.getResources().getIdentifier(
            resourceName, "raw", context.getApplicationContext().getPackageName()
        )
        if (resourceId == 0) return null
        val stream: InputStream = context.getResources().openRawResource(resourceId)
        try {
            return JSONObject(convertStreamToString(stream))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun parseCountries(jsonCountries: JSONObject?): List<Country> {
        val countries: MutableList<Country> = ArrayList()
        val iter: Iterator<String> = jsonCountries!!.keys()
        while (iter.hasNext()) {
            val key: String = iter.next()
            try {
                val value: String = jsonCountries.get(key) as String
                countries.add(Country(key, value))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return countries
    }

    fun convertStreamToString(`is`: InputStream?): String {
        val s: Scanner = Scanner(`is`).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}
