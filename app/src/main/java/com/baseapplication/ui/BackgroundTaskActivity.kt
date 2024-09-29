package com.baseapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.baseapplication.R
import com.baseapplication.workmanager.BackgroundWorker
import java.util.concurrent.TimeUnit

//@AndroidEntryPoint
class BackgroundTaskActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_task)
        findViewById<View>(R.id.notificaion).setOnClickListener(object : View.OnClickListener {
            public override fun onClick(view: View) {
                //Enqueuing the work request
//              setOntimeTask();
                setPriodicallyTask()
            }
        })
    }

    fun setOntimeTask() {
        //This is the subclass of our WorkRequest
        val workRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(BackgroundWorker::class.java)
            .build()
        //A click listener for the button
        //inside the onClick method we will perform the work
        WorkManager.getInstance().enqueue(workRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, object : Observer<WorkInfo?> {
                public override fun onChanged(workInfo: WorkInfo?) {
                    //Displaying the status into TextView
                    Log.e("onchange", "onChanged: " + workInfo!!.state.name)
                }
     })
    }

    fun setPriodicallyTask() {
        val periodicWorkRequest1: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            BackgroundWorker::class.java,
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MINUTES
        ).build()
        val workManager: WorkManager = WorkManager.getInstance()
        workManager.enqueue(periodicWorkRequest1)
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest1.id)
            .observe(this, object : Observer<WorkInfo?> {
                public override fun onChanged(workInfo: WorkInfo?) {
                    if (workInfo != null) {
                        Log.d("periodicWorkRequest", "Status changed to : " + workInfo.state)
                    }
                }
            })
    }
}