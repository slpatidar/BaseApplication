package com.sp.base.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sp.base.R;
import com.sp.base.workmanager.BackgroundWorker;

import java.util.concurrent.TimeUnit;

public class BackgroundTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_task);
        findViewById(R.id.notificaion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enqueuing the work request
//              setOntimeTask();
                setPriodicallyTask();
            }
        });
    }

    public void setOntimeTask() {
        //This is the subclass of our WorkRequest
        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(BackgroundWorker.class).build();
        //A click listener for the button
        //inside the onClick method we will perform the work
        WorkManager.getInstance().enqueue(workRequest);
        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        //Displaying the status into TextView
                        Log.e("onchange", "onChanged: " + workInfo.getState().name());
                    }
                });
    }
    public void setPriodicallyTask() {

        final PeriodicWorkRequest periodicWorkRequest1 = new PeriodicWorkRequest.Builder(BackgroundWorker.class,PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MINUTES)
                .build();

        WorkManager workManager =  WorkManager.getInstance();

        workManager.enqueue(periodicWorkRequest1);

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest1.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null) {
                            Log.d("periodicWorkRequest", "Status changed to : " + workInfo.getState());
                        }
                    }
                });
    }
}