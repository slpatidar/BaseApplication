package com.baseapplication.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.baseapplication.R

class BackgroundWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    public override fun doWork(): Result {
        //  //getting the input data
        //  String taskDesc = getInputData().getString(TASK_DESC);
        displayNotification("My Worker", "Hey I finished my work")
        //        //setting output data
//        Data data = new Data.Builder()
//                .putString("TASK_DESC", "The conclusion of the task")
//                .build()

//        setOutputData(data);
        return Result.success()
    }

    public override fun onStopped() {
        super.onStopped()
        Log.e("BackgroundWorker", "onStopped: ")
    }

    /*
     * The method is doing nothing but only generating
     * a simple notification
     * If you are confused about it
     * you should check the Android Notification Tutorial
     * */
    private fun displayNotification(title: String, task: String) {
        val notificationManager: NotificationManager = getApplicationContext().getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel: NotificationChannel = NotificationChannel(
                "simplifiedcoding",
                "simplifiedcoding",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(1, notification.build())
    }
}
