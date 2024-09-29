package com.baseapplication.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.baseapplication.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@Suppress("unused")
class NotificationUtils(private val mContext: Context) {
    private val separatorString: String = "<1>"

    // TODO : Add Alpha and no alpha images for the notifications
    private val appIconAlpha: Int = R.drawable.ic_launcher_foreground
    private val appIconNoAlpha: Int = R.drawable.ic_launcher_foreground
    private val CHANNEL_ID: String? = null
    fun showUniqueNotification(
        messageOrNotification: String, pendingIntent: PendingIntent,
        dismissIntent: PendingIntent,
        showNotification: Boolean, subText: String?, notificationType: Int
    ) {
        if (showNotification) {
            val bigTextStyle: NotificationCompat.BigTextStyle =
                NotificationCompat.BigTextStyle().bigText(subText)
            showAllKindNotification(
                messageOrNotification, pendingIntent, dismissIntent, subText, bigTextStyle,
                notificationType, (System.currentTimeMillis() / 1000).toInt()
            )
        }
    }

    // Send false to remove previous notification on receiving new and true to show every Notification separately
    private fun showAllKindNotification(
        messageOrNotification: String, pendingIntent: PendingIntent,
        dismissIntent: PendingIntent,
        subText: String?, bigTextStyle: NotificationCompat.Style,
        notificationType: Int, notificationId: Int
    ) {

        // Sets an ID for the notification, so it can be updated.
        val notifyID: Int = 1
        val CHANNEL_ID: String = mContext.getString(R.string.app_name) // The id of the channel.
        val name: CharSequence =
            mContext.getString(R.string.app_name) // The user-visible name of the channel.
        var mChannel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance: Int = NotificationManager.IMPORTANCE_HIGH
            mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        }
        LogUtil.printLog(TAG, "showAllKindNotification: " + pendingIntent)
        var mBuilder: NotificationCompat.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBuilder = NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), appIconAlpha))
                .setContentTitle(messageOrNotification)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentText(subText)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) //                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setContentIntent(pendingIntent)
                .setStyle(bigTextStyle)
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(mContext, R.color.black))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mBuilder != null) {
                mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            }
        }
        val notificationManager: NotificationManager? =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (notificationManager != null) {
            if (mBuilder != null) {
                notificationManager.notify(notificationId, mBuilder.build())
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel((mChannel)!!)
            }
        }
    }

    @JvmOverloads
    fun showNotificationMessage(
        title: String,
        message: String?,
        timeStamp: String,
        intent: Intent,
        imageUrl: String? = null
    ) {
        // Check for empty push message
        if (TextUtils.isEmpty(message)) return


        // notification icon
        val icon: Int = R.mipmap.ic_launcher
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val resultPendingIntent: PendingIntent = PendingIntent.getActivity(
            mContext,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(mContext, (CHANNEL_ID)!!)
        val alarmSound: Uri = Uri.parse(
            (ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification")
        )
        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl!!.length > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                val bitmap: Bitmap? = getBitmapFromURL(imageUrl)
                if (bitmap != null) {
                    showBigNotification(
                        bitmap,
                        mBuilder,
                        icon,
                        title,
                        message,
                        timeStamp,
                        resultPendingIntent,
                        alarmSound
                    )
                } else {
                    showSmallNotification(
                        mBuilder,
                        icon,
                        title,
                        message,
                        timeStamp,
                        resultPendingIntent,
                        alarmSound
                    )
                }
            }
        } else {
            showSmallNotification(
                mBuilder,
                icon,
                title,
                message,
                timeStamp,
                resultPendingIntent,
                alarmSound
            )
            //            playNotificationSound();
        }
    }

    private fun showSmallNotification(
        mBuilder: NotificationCompat.Builder,
        icon: Int,
        title: String,
        message: String?,
        timeStamp: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri
    ) {
        val inboxStyle: NotificationCompat.InboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.addLine(message)
        val notification: Notification
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent) //                .setSound(alarmSound)
            .setStyle(inboxStyle)
            .setWhen(getTimeMilliSec(timeStamp))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
            .setContentText(message)
            .build()
        val notificationManager: NotificationManager? =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (notificationManager != null) {
            notificationManager.notify(12, notification)
        }
    }

    private fun showBigNotification(
        bitmap: Bitmap,
        mBuilder: NotificationCompat.Builder,
        icon: Int,
        title: String,
        message: String?,
        timeStamp: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri
    ) {
        val bigPictureStyle: NotificationCompat.BigPictureStyle =
            NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(title)
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
        bigPictureStyle.bigPicture(bitmap)
        val notification: Notification
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent) //                .setSound(alarmSound)
            .setStyle(bigPictureStyle)
            .setWhen(getTimeMilliSec(timeStamp))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
            .setContentText(message)
            .build()
        val notificationManager: NotificationManager? =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (notificationManager != null) {
            notificationManager.notify(121, notification)
        }
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    fun getBitmapFromURL(strURL: String?): Bitmap? {
        try {
            val url: URL = URL(strURL)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    // Playing notification sound
    fun playNotificationSound() {
        try {
            val alarmSound: Uri = Uri.parse(
                (ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + mContext.getPackageName() + "/raw/notification")
            )
            val r: Ringtone = RingtoneManager.getRingtone(mContext, alarmSound)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val TAG: String = NotificationUtils::class.java.getSimpleName()
        fun clearAllNotification(context: Context) {
            val notificationManager: NotificationManager? =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            assert(notificationManager != null)
            notificationManager!!.cancelAll()
        }

        /**
         * Method checks if the app is in background or not
         */
        fun isAppIsInBackground(context: Context): Boolean {
            var isInBackground: Boolean = true
            val am: ActivityManager? =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            val runningProcesses: List<RunningAppProcessInfo>?
            if (am != null) {
                runningProcesses = am.getRunningAppProcesses()
                if (runningProcesses != null) {
                    for (processInfo: RunningAppProcessInfo in runningProcesses) {
                        if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            for (activeProcess: String in processInfo.pkgList) {
                                if ((activeProcess == context.getPackageName())) {
                                    isInBackground = false
                                }
                            }
                        }
                    }
                }
            }
            return isInBackground
        }

        // Clears notification tray messages
        fun clearNotifications(context: Context) {
            val notificationManager: NotificationManager? =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            if (notificationManager != null) {
                notificationManager.cancelAll()
            }
        }

        fun getTimeMilliSec(timeStamp: String?): Long {
            @SuppressLint("SimpleDateFormat") val format: SimpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                val date: Date? = format.parse(timeStamp)
                if (date != null) {
                    return date.getTime()
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return 0
        }
    }
}
