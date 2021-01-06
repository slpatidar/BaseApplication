package com.sp.base.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;


import com.sp.base.R;

import java.util.Objects;

public class DialogUtil {
    private static ProgressDialog sProgressDialog;

    public static void showProgressDialog(Context context) {
        try {
            if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                if (sProgressDialog != null) {
                    if (sProgressDialog.isShowing()) {
                        sProgressDialog.cancel();
                    }
                    sProgressDialog = null;
                }
                sProgressDialog = new ProgressDialog(context);
                sProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                sProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                sProgressDialog.setMessage("Please wait...");
                sProgressDialog.setCancelable(false);
                sProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressDialog() {
        try {
            if (sProgressDialog != null) sProgressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dialogWithOk(Context context, String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setNeutralButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    public static void dialogWithOk(Context context, Drawable drawable, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(drawable)
                .setTitle(title)
                .setMessage(msg)
                .setNeutralButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    public static void dialogWithYesNo(Context context, String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // continue with delete
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .show();
    }

    public static void openAlertDialog(Context context, String title, String message, String positiveBtnText, String negativeBtnText,
                                       final OnDialogButtonClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(positiveBtnText, (dialog, which) -> {
            dialog.dismiss();
            listener.onPositiveButtonClicked();

        });

        builder.setNegativeButton(negativeBtnText, (dialog, which) -> {
            dialog.dismiss();
            listener.onNegativeButtonClicked();

        });
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.create().show();
    }

    public static void openAlertDialogWithOKButton(Context context, String message, String positiveBtnText) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(positiveBtnText, (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setMessage(message);
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
        Objects.requireNonNull(alert.getWindow()).getAttributes();
        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        assert textView != null;
        textView.setTextSize(18);

    }

    private Activity mActivity;
    private Dialog mDialog;

    //..we need the context else we can not create the dialog so get context in constructor
    public DialogUtil(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void showDialog(String message) {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog.cancel();
                mDialog = null;
            }
        }
        mDialog = new Dialog(mActivity, R.style.ThemeDialogCustomFullScreen);
        View view = mActivity.getLayoutInflater().inflate(R.layout.layout_progress_loader, null);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        //...that's the layout i told you will inflate later
        mDialog.setContentView(view);
        AppCompatTextView appCompatTextView = mDialog.findViewById(R.id.mMessage);
        appCompatTextView.setText(message);
        //...finaly show it
        // For Full Screen Dialog Box
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mDialog.getWindow().setStatusBarColor(mActivity.getResources().getColor(R.color.colorSemiTransparentWhite));
        }

        if (!mActivity.isDestroyed() && !mActivity.isFinishing() && !mDialog.isShowing())
            mDialog.show();
    }

    //..also create a method which will hide the dialog when some work is done
    public void hideDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }
    interface OnDialogButtonClickListener {
        public void onPositiveButtonClicked();

        void onNegativeButtonClicked();
    }
}
