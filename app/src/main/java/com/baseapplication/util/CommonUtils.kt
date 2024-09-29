package com.baseapplication.util

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.baseapplication.R
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.SecureRandom
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/*
 * CommonUtils is used for all common methods
 * */
class CommonUtils() {
    fun intAmountFormatter(mAmount: Double): String {
        var sAmount: String = mAmount.toString()
        var formattedNumber: String = sAmount
        val intAmount: Int
        try {
            val str: String = NumberFormat.getCurrencyInstance(Locale("en", "us")).format(mAmount)
            sAmount = str.substring(1) //String.format("%,.2f", Double.parseDouble(dAmount));
            LogUtil.printLog("amount ", str + "  " + sAmount)
            sAmount = sAmount.replace(",", "")
            var dAmount: Double = 0.0
            try {
                dAmount = sAmount.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            intAmount = (dAmount).toInt()
            //            sAmount = String.valueOf(intAmount);
            val formatter: NumberFormat = DecimalFormat("#,###")
            formattedNumber = formatter.format(intAmount.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return formattedNumber
    }

    fun PixelsToDp(context: Context, pixels: Int): Float {
        val resource: Resources = context.getResources()
        return (pixels / (resource.getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
    }

    companion object {
        private val TAG: String = CommonUtils::class.java.getSimpleName()
        var PERMISSIONS_REQUEST_READ_PHONE_STATE: Int = 0
        var act: FragmentActivity? = null
        var IMEINumber: String? = null

        /**
         * this method return whole device info
         *
         * @param context context
         * @return device info
         */
        fun getDeviceType(context: Context): String {
            val isTablet: Boolean = context.getResources().getBoolean(R.bool.isTablet)
            val deviceType: String =
                Constants.ANDROID + "-" + Constants.DEVICE_OS_VALUE + "-" + (if (isTablet) Constants.TABLET else Constants.PHONE) + "-" + Build.MANUFACTURER + " " + Build.MODEL.replace(
                    "-",
                    " "
                )
            try {
                return URLEncoder.encode(deviceType, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                LogUtil.printLog(TAG, e.toString())
                return deviceType.replace(" ", "%20")
            }
        }

        val deviceModel: String
            /**
             * this method return device model like Galaxy 9
             *
             * @return device model
             */
            get() {
                return Build.MODEL
            }

        /**
         * this method return device type PHONE, TABLET
         *
         * @param context context
         * @return device type
         */
        fun getDeviceTypePhoneOrTablet(context: Context): String {
            val isTablet: Boolean = context.getResources().getBoolean(R.bool.isTablet)
            return (if (isTablet) Constants.TABLET else Constants.PHONE).uppercase(
                Locale.getDefault()
            )
        }

        val oSVersion: String
            get() {
                var os: String = ""
                try {
                    os = Build.VERSION.RELEASE
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return os
            }

        /**
         * converting bytes to Hex string
         *
         * @param bytes data
         * @return hex string
         */
//        fun bytesToHex(bytes: ByteArray): String {
//            val HEX_ARRAY: CharArray = "0123456789ABCDEF".toCharArray()
//            val hexChars: CharArray = CharArray(bytes.size * 2)
//            for (j in bytes.indices) {
//                val v: Int = bytes.get(j).toInt() and 0xFF
//                hexChars.get(j * 2) = HEX_ARRAY.get(v ushr 4)
//                hexChars.get(j * 2 + 1) = HEX_ARRAY.get(v and 0x0F)
//            }
//            return String(hexChars)
//        }

        // To show soft keyboard
        fun showKeyBoard(activity: Activity) {
            try {
                val imm: InputMethodManager? =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if (imm != null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // For formatting amount according country
        fun getFormattedAmount(amount: String, editTextAmount: EditText): String? {
            var amount: String = amount
            var formattedString: String? = null
            if (amount.length == 0) {
                amount = "0.00"
            }
            try {
                val cleanString: String = amount.replace("\\D".toRegex(), "")
                val parsed: Double = cleanString.toDouble()
                val numberFormat: NumberFormat =
                    NumberFormat.getCurrencyInstance(Locale("en", "US"))
                val formatted: String = numberFormat.format((parsed / 100))
                    .replace(numberFormat.getCurrency().getSymbol(), "")
                val formattedsymbol: String = formatted.replace(
                    (numberFormat as DecimalFormat).getDecimalFormatSymbols().getCurrencySymbol(),
                    ""
                )
                //  formattedString = formatted.substring(1).trim();
                formattedString = formattedsymbol.replace("\\s+".toRegex(), "")
                if (parsed == 0.0) {
                    editTextAmount.setText("")
                } else {
                    editTextAmount.setText(formattedString)
                    editTextAmount.setSelection(editTextAmount.getText().length)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return formattedString
        }

        /**
         * this method format the Amount in decimal and ,
         *
         * @param amount amount
         * @return formatted amount
         */
        fun formatAmountInDecimal(amount: String): String {
            var formattedAmount: String = amount
            try {
                formattedAmount = String.format(Locale.US, "%,.2f", formattedAmount.toDouble())
            } catch (e: Exception) {
                e.printStackTrace()
                return amount
            }
            return formattedAmount
        }

        fun capitalizeFirstCharacter(input: String?): String? {
            if (TextUtils.isEmpty(input)) {
                return input
            }
            val stringBuilder: StringBuilder = StringBuilder(input)
            stringBuilder.setCharAt(0, stringBuilder.get(0).uppercaseChar())
            return stringBuilder.toString()
        }

        /**
         * navigate user to app store
         *
         * @param context context
         */
        fun openAppMarketLink(context: Context) {
            val appPackageName: String = context.getPackageName()
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + appPackageName)
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                    )
                )
            }
        }

        fun generateRandomString(lenght: Int): String {
            val secureRandom: SecureRandom = SecureRandom()
            val letters: String = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@"
            var pw: String = ""
            for (i in 0 until lenght) {
                val index: Int = (secureRandom.nextDouble() * letters.length).toInt()
                pw += letters.substring(index, index + 1)
            }
            LogUtil.printLog(TAG, "String str=" + pw)
            return pw
        }

        private fun requestReadPhoneStatePermission(activity: FragmentActivity?) {
            try {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (activity)!!,
                        Manifest.permission.READ_PHONE_STATE
                    )
                ) {
                    // Provide an additional rationale to the user if the permission was not granted
                    // and the user would benefit from additional context for the use of the permission.
                    // For example if the user has previously denied the permission.
                    ActivityCompat.requestPermissions(
                        (activity), arrayOf(Manifest.permission.READ_PHONE_STATE),
                        PERMISSIONS_REQUEST_READ_PHONE_STATE
                    )
                    doPermissionGrantedStuffs(activity)
                } else {
                    // READ_PHONE_STATE permission has not been granted yet. Request it directly.
                    ActivityCompat.requestPermissions(
                        (activity), arrayOf(Manifest.permission.READ_PHONE_STATE),
                        PERMISSIONS_REQUEST_READ_PHONE_STATE
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun doPermissionGrantedStuffs(activity: FragmentActivity?) {
            try {
                //Have an  object of TelephonyManager
                val tm: TelephonyManager =
                    activity!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                if (Build.VERSION.SDK_INT >= 29) {
                    IMEINumber = Settings.Secure.getString(
                        activity.getContentResolver(), Settings.Secure.ANDROID_ID
                    )
                } else if (Build.VERSION.SDK_INT >= 26) {
                    IMEINumber = tm.getImei()
                } else {
                    IMEINumber = tm.getDeviceId()
                }
                // IMEINumber = tm.getDeviceId();
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // get device unique ID
        fun getImeiNo(activity: FragmentActivity): String? {
            act = activity
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Check if the READ_PHONE_STATE permission is already available.
                if ((ActivityCompat.checkSelfPermission(
                        activity.getApplicationContext(),
                        Manifest.permission.READ_PHONE_STATE
                    )
                            != PackageManager.PERMISSION_GRANTED)
                ) {
                    // READ_PHONE_STATE permission has not been granted.
                    requestReadPhoneStatePermission(act)
                } else {
                    // READ_PHONE_STATE permission is already been granted.
                    doPermissionGrantedStuffs(act)
                }
            } else {
                val telephonyManager: TelephonyManager =
                    activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                IMEINumber = telephonyManager.getDeviceId()
            }
            return IMEINumber
        }

        /**
         * this method make per word first letter capital
         * @param s
         * @return
         */
//        fun makeWordFirstLetterCapital(s: String): String {
//            val DELIMITERS: String = " '-/"
//            val sb: StringBuilder = StringBuilder()
//            var capNext: Boolean = true
//            for (c: Char in s.toCharArray()) {
//                c = if ((capNext)) c.uppercaseChar() else c.lowercaseChar()
//                sb.append(c)
//                capNext = (DELIMITERS.indexOf((c.code).toChar()) >= 0)
//            }
//            return sb.toString()
//        }

        fun convertDpToPixel(dp: Float, context: Context): Int {
            val resources: Resources = context.getResources()
            val metrics: DisplayMetrics = resources.getDisplayMetrics()
            val px: Float = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            return px.toInt()
        }

        // To hide soft keyboard
        fun hideKeyBoard(activity: Activity) {
            // Check if no view has focus:
            val view: View? = activity.getCurrentFocus()
            if (view != null) {
                try {
                    val imm: InputMethodManager? =
                        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                    if (imm != null) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        // For formatting amount according country
        fun getFormattedAmountTextView(amount: String, editTextAmount: AppCompatTextView): String? {
            var formattedString: String? = null
            try {
                val cleanString: String = amount.replace("\\D".toRegex(), "")
                var parsed: Double = 0.0
                try {
                    if (!cleanString.isEmpty()) parsed = cleanString.toDouble()
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
                val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance()
                val formatted: String = numberFormat.format((parsed / 100))
                    .replace(numberFormat.getCurrency().getSymbol(), "")
                val formattedsymbol: String = formatted.replace(
                    (numberFormat as DecimalFormat).getDecimalFormatSymbols().getCurrencySymbol(),
                    ""
                )
                formattedString = formattedsymbol.replace("\\s+".toRegex(), "")
                if (parsed == 0.0) {
                    editTextAmount.setText("")
                } else {
                    editTextAmount.setText(formattedString)
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return formattedString
        }
    }
}
