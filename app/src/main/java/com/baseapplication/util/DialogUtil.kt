package com.baseapplication.util

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.baseapplication.R
import java.util.Objects

class DialogUtil //..we need the context else we can not create the dialog so get context in constructor
    (private val mActivity: Activity) {
    private var mDialog: Dialog? = null
    fun showDialog(message: String?) {
        if (mDialog != null) {
            if (mDialog!!.isShowing()) {
                mDialog!!.dismiss()
                mDialog!!.cancel()
                mDialog = null
            }
        }
        mDialog = Dialog(mActivity, R.style.ThemeDialogCustomFullScreen)
        val view: View =
            mActivity.getLayoutInflater().inflate(R.layout.layout_progress_loader, null)
        mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //...set cancelable false so that it's never get hidden
        mDialog!!.setCancelable(false)
        mDialog!!.setCanceledOnTouchOutside(false)
        //...that's the layout i told you will inflate later
        mDialog!!.setContentView(view)
        val appCompatTextView: AppCompatTextView = mDialog!!.findViewById(R.id.mMessage)
        appCompatTextView.setText(message)
        //...finaly show it
        // For Full Screen Dialog Box
        if (mDialog!!.getWindow() != null) {
            mDialog!!.getWindow()!!.setFlags(0, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            mDialog!!.getWindow()!!.setStatusBarColor(
                mActivity.getResources().getColor(R.color.colorSemiTransparentWhite)
            )
        }
        if (!mActivity.isDestroyed() && !mActivity.isFinishing() && !mDialog!!.isShowing()) mDialog!!.show()
    }

    //..also create a method which will hide the dialog when some work is done
    fun hideDialog() {
        if (mDialog != null) mDialog!!.dismiss()
    }

    open interface OnDialogButtonClickListener {
        fun onPositiveButtonClicked()
        fun onNegativeButtonClicked()
    }

    companion object {
        private var sProgressDialog: ProgressDialog? = null
        fun showProgressDialog(context: Context) {
            try {
                if (!(context as Activity).isFinishing() && !context.isDestroyed()) {
                    if (sProgressDialog != null) {
                        if (sProgressDialog!!.isShowing()) {
                            sProgressDialog!!.cancel()
                        }
                        sProgressDialog = null
                    }
                    sProgressDialog = ProgressDialog(context)
                    sProgressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    sProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    sProgressDialog!!.setMessage("Please wait...")
                    sProgressDialog!!.setCancelable(false)
                    sProgressDialog!!.show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun dismissProgressDialog() {
            try {
                if (sProgressDialog != null) sProgressDialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun dialogWithOk(context: Context?, msg: String?, title: String?) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                (context)!!
            )
            builder.setTitle(title)
                .setMessage(msg)
                .setNeutralButton(
                    android.R.string.ok,
                    DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int -> dialog.dismiss() })
                )
                .show()
        }

        fun dialogWithOk(context: Context?, drawable: Drawable?, title: String?, msg: String?) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                (context)!!
            )
            builder.setIcon(drawable)
                .setTitle(title)
                .setMessage(msg)
                .setNeutralButton(
                    android.R.string.ok,
                    DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int -> dialog.dismiss() })
                )
                .show()
        }

        fun dialogWithYesNo(context: Context?, msg: String?, title: String?) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                (context)!!
            )
            builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(
                    android.R.string.yes,
                    DialogInterface.OnClickListener({ dialog: DialogInterface?, which: Int -> })
                )
                .setNegativeButton(
                    android.R.string.no,
                    DialogInterface.OnClickListener({ dialog: DialogInterface?, which: Int -> })
                )
                .show()
        }

        fun openAlertDialog(
            context: Context?,
            title: String?,
            message: String?,
            positiveBtnText: String?,
            negativeBtnText: String?,
            listener: OnDialogButtonClickListener
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                (context)!!
            )
            builder.setPositiveButton(
                positiveBtnText,
                DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                    listener.onPositiveButtonClicked()
                })
            )
            builder.setNegativeButton(
                negativeBtnText,
                DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                    listener.onNegativeButtonClicked()
                })
            )
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setCancelable(false)
            builder.create().show()
        }

        fun openAlertDialogWithOKButton(
            context: Context?,
            message: String?,
            positiveBtnText: String?
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                (context)!!
            )
            builder.setPositiveButton(
                positiveBtnText,
                DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int -> dialog.dismiss() })
            )
            builder.setMessage(message)
            builder.setCancelable(false)
            val alert: AlertDialog = builder.create()
            alert.show()
            Objects.requireNonNull(alert.getWindow())?.getAttributes()
            val textView: TextView? = alert.findViewById<View>(android.R.id.message) as TextView?
            assert(textView != null)
            textView!!.setTextSize(18f)
        }
    }
}
