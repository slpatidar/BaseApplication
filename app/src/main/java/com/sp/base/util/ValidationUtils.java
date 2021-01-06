package com.sp.base.util;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    Resources mResources = Resources.getSystem();

    /* For check edit text is empty */
    public static boolean isValidate(AppCompatEditText... et2) {
        boolean isValidate = false;
        for (AppCompatEditText textInputEditText: et2) {
            CharSequence target = textInputEditText.getText();
            if (!TextUtils.isEmpty(target)) {
                isValidate = true;
            } else {
                textInputEditText.requestFocus();
                textInputEditText.setError("Field is empty");
                isValidate = false;
                break;
            }
        }
        return isValidate;
    }

    /* For check email is valid or not */
    public static boolean isValidEmail(AppCompatEditText textInputEditText) {
        CharSequence target = textInputEditText.getText();
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /* For check email is valid or not */
    public static boolean isValidDate(AppCompatEditText textInputEditText) {
        CharSequence target = textInputEditText.getText();
        return (!TextUtils.isEmpty(target));
    }

    // For check Mobile Number is valid or not
    public static boolean isValidMobile(AppCompatEditText textInputEditText) {
        CharSequence phone = textInputEditText.getText();
        if (!TextUtils.isEmpty(phone)) {
            if (textInputEditText.getText().length() > 9) {
                return true;
            }else {
                textInputEditText.requestFocus();
                textInputEditText.setError("Invalid Mobile No");
                return false;
            }

        } else {
            textInputEditText.requestFocus();
            textInputEditText.setError("Field is empty");
            return false;
        }
    }

    public boolean isValidEmail(String string) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }


    public boolean isValidPassword(String string, boolean allowSpecialChars) {
        String PATTERN;
        if (allowSpecialChars) {
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
            PATTERN = "^[a-zA-Z@#$%]\\w{5,19}$";
        } else {
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
            PATTERN = "^[a-zA-Z]\\w{5,19}$";
        }


        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static boolean isValidName(TextInputEditText... et2) {
        for (TextInputEditText textInputEditText: et2) {
            CharSequence name = textInputEditText.getText();
            if (Pattern.matches("^[\\p{L} .'-]+$", name)) {
                if(name.length() < 2){
                    textInputEditText.setError("Text to short");
                    return false;
                }else {
                    return true;
                }
            }else{
                textInputEditText.setError("Invalid Text");
                return false;
            }
        }
        return false;
    }

    public boolean isNullOrEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public boolean isNumeric(String string) {
        return TextUtils.isDigitsOnly(string);
    }

    public static boolean isValidDOB(String string) {

        return true;
    }
}