package com.baseapplication.util

import android.content.res.Resources
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidationUtils() {
    var mResources: Resources = Resources.getSystem()
    fun isValidEmail(string: String?): Boolean {
        val EMAIL_PATTERN: String =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher: Matcher = pattern.matcher(string)
        return matcher.matches()
    }

    fun isValidPassword(string: String?, allowSpecialChars: Boolean): Boolean {
        val PATTERN: String
        if (allowSpecialChars) {
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
            PATTERN = "^[a-zA-Z@#$%]\\w{5,19}$"
        } else {
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
            PATTERN = "^[a-zA-Z]\\w{5,19}$"
        }
        val pattern: Pattern = Pattern.compile(PATTERN)
        val matcher: Matcher = pattern.matcher(string)
        return matcher.matches()
    }

    fun isNullOrEmpty(string: String?): Boolean {
        return TextUtils.isEmpty(string)
    }

    fun isNumeric(string: String?): Boolean {
        return TextUtils.isDigitsOnly(string)
    }

    companion object {
        /* For check edit text is empty */
        fun isValidate(vararg et2: AppCompatEditText): Boolean {
            var isValidate: Boolean = false
            for (textInputEditText: AppCompatEditText in et2) {
                val target: CharSequence? = textInputEditText.getText()
                if (!TextUtils.isEmpty(target)) {
                    isValidate = true
                } else {
                    textInputEditText.requestFocus()
                    textInputEditText.setError("Field is empty")
                    isValidate = false
                    break
                }
            }
            return isValidate
        }

        /* For check email is valid or not */
        fun isValidEmail(textInputEditText: AppCompatEditText): Boolean {
            val target: CharSequence? = textInputEditText.getText()
            return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches())
        }

        /* For check email is valid or not */
        fun isValidDate(textInputEditText: AppCompatEditText): Boolean {
            val target: CharSequence? = textInputEditText.getText()
            return (!TextUtils.isEmpty(target))
        }

        // For check Mobile Number is valid or not
        fun isValidMobile(textInputEditText: AppCompatEditText): Boolean {
            val phone: CharSequence? = textInputEditText.getText()
            if (!TextUtils.isEmpty(phone)) {
                if (textInputEditText.getText()!!.length > 9) {
                    return true
                } else {
                    textInputEditText.requestFocus()
                    textInputEditText.setError("Invalid Mobile No")
                    return false
                }
            } else {
                textInputEditText.requestFocus()
                textInputEditText.setError("Field is empty")
                return false
            }
        }

        fun isValidName(vararg et2: TextInputEditText): Boolean {
            for (textInputEditText: TextInputEditText in et2) {
                val name: CharSequence? = textInputEditText.getText()
                if (Pattern.matches("^[\\p{L} .'-]+$", name)) {
                    if (name!!.length < 2) {
                        textInputEditText.setError("Text to short")
                        return false
                    } else {
                        return true
                    }
                } else {
                    textInputEditText.setError("Invalid Text")
                    return false
                }
            }
            return false
        }

        fun isValidDOB(string: String?): Boolean {
            return true
        }
    }
}