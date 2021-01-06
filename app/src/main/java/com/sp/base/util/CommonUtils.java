package com.sp.base.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import com.sp.base.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/*
 * CommonUtils is used for all common methods
 * */
public class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();
    static int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    static FragmentActivity act;
    static String IMEINumber;

    /**
     * this method return whole device info
     *
     * @param context context
     * @return device info
     */
    public static String getDeviceType(Context context) {

        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        String deviceType = Constants.ANDROID + "-" + Constants.DEVICE_OS_VALUE + "-" + (isTablet ? Constants.TABLET : Constants.PHONE) + "-" + Build.MANUFACTURER + " " + Build.MODEL.replace("-", " ");
        try {
            return URLEncoder.encode(deviceType, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.printLog(TAG, e.toString());
            return deviceType.replace(" ", "%20");
        }
    }

    /**
     * this method return device model like Galaxy 9
     *
     * @return device model
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * this method return device type PHONE, TABLET
     *
     * @param context context
     * @return device type
     */
    public static String getDeviceTypePhoneOrTablet(Context context) {
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        return (isTablet ? Constants.TABLET : Constants.PHONE).toUpperCase();
    }

    public static String getOSVersion() {
        String os = "";
        try {
            os = Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return os;
    }


    public String intAmountFormatter(double mAmount) {
        String sAmount = String.valueOf(mAmount);
        String formattedNumber = sAmount;
        int intAmount;
        try {
            String str = NumberFormat.getCurrencyInstance(new Locale("en", "us")).format(mAmount);
            sAmount = str.substring(1);//String.format("%,.2f", Double.parseDouble(dAmount));
            LogUtil.printLog("amount ", str + "  " + sAmount);
            sAmount = sAmount.replace(",", "");
            double dAmount = 0;
            try {
                dAmount = Double.parseDouble(sAmount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            intAmount = (int) (dAmount);
//            sAmount = String.valueOf(intAmount);
            NumberFormat formatter = new DecimalFormat("#,###");
            formattedNumber = formatter.format(intAmount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedNumber;
    }
    /**
     * converting bytes to Hex string
     *
     * @param bytes data
     * @return hex string
     */
    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }




    public float PixelsToDp(Context context, int pixels) {
        Resources resource = context.getResources();
        return pixels / (resource.getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    // To show soft keyboard
    public static void showKeyBoard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // For formatting amount according country
    public static String getFormattedAmount(String amount, EditText editTextAmount) {
        String formattedString = null;
        if (amount.length() == 0) {
            amount = "0.00";
        }
        try {
            String cleanString = amount.replaceAll("\\D", "");

            double parsed = Double.parseDouble(cleanString);
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
            String formatted = numberFormat.format((parsed / 100)).replace(numberFormat.getCurrency().getSymbol(), "");
            String formattedsymbol = formatted.replace(((DecimalFormat) numberFormat).getDecimalFormatSymbols().getCurrencySymbol(), "");
            //  formattedString = formatted.substring(1).trim();
            formattedString = formattedsymbol.replaceAll("\\s+", "");
            if (parsed == 0.0) {
                editTextAmount.setText("");
            } else {
                editTextAmount.setText(formattedString);
                editTextAmount.setSelection(editTextAmount.getText().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedString;
    }

    /**
     * this method format the Amount in decimal and ,
     *
     * @param amount amount
     * @return formatted amount
     */
    public static String formatAmountInDecimal(String amount) {
        String formattedAmount = amount;
        try {
            formattedAmount = String.format(Locale.US, "%,.2f", Double.parseDouble(formattedAmount));
        } catch (Exception e) {
            e.printStackTrace();
            return amount;
        }
        return formattedAmount;
    }

    public static String capitalizeFirstCharacter(String input) {

        if (TextUtils.isEmpty(input)) {
            return input;
        }
        StringBuilder stringBuilder = new StringBuilder(input);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        return stringBuilder.toString();
    }
    /**
     * navigate user to app store
     *
     * @param context context
     */
    public static void openAppMarketLink(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    public static String generateRandomString(int lenght) {
        SecureRandom secureRandom = new SecureRandom();
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
        String pw = "";
        for (int i = 0; i < lenght; i++) {
            int index = (int) (secureRandom.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        LogUtil.printLog(TAG, "String str=" + pw);
        return pw;
    }

    private static void requestReadPhoneStatePermission(FragmentActivity activity) {
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_PHONE_STATE)) {
                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // For example if the user has previously denied the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
                doPermissionGrantedStuffs(activity);

            } else {
                // READ_PHONE_STATE permission has not been granted yet. Request it directly.
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doPermissionGrantedStuffs(FragmentActivity activity) {
        try {
            //Have an  object of TelephonyManager
            TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

            if (Build.VERSION.SDK_INT >= 29) {
                IMEINumber = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else if (Build.VERSION.SDK_INT >= 26) {
                IMEINumber = tm.getImei();
            } else {
                IMEINumber = tm.getDeviceId();
            }
            // IMEINumber = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get device unique ID
    public static String getImeiNo(FragmentActivity activity) {
        act = activity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Check if the READ_PHONE_STATE permission is already available.
            if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has not been granted.
                requestReadPhoneStatePermission(act);
            } else {
                // READ_PHONE_STATE permission is already been granted.
                doPermissionGrantedStuffs(act);
            }
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            IMEINumber = telephonyManager.getDeviceId();
        }
        return IMEINumber;
    }

    /**
     * this method make per word first letter capital
     * @param s
     * @return
     */
    public static String makeWordFirstLetterCapital(String s) {
        final String DELIMITERS = " '-/";
        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (DELIMITERS.indexOf((int) c) >= 0);
        }
        return sb.toString();
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }


    // To hide soft keyboard
    public static void hideKeyBoard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            try {

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // For formatting amount according country
    public static String getFormattedAmountTextView(String amount, AppCompatTextView editTextAmount) {
        String formattedString = null;
        try {
            String cleanString = amount.replaceAll("\\D", "");
            double parsed = 0;

            try {
                if (!cleanString.isEmpty())
                    parsed = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            String formatted = numberFormat.format((parsed / 100)).replace(numberFormat.getCurrency().getSymbol(), "");
            String formattedsymbol = formatted.replace(((DecimalFormat) numberFormat).getDecimalFormatSymbols().getCurrencySymbol(), "");

            formattedString = formattedsymbol.replaceAll("\\s+", "");

            if (parsed == 0.0) {
                editTextAmount.setText("");
            } else {
                editTextAmount.setText(formattedString);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return formattedString;
    }
}
