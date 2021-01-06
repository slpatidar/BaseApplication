package com.sp.base.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.sp.base.R;
import com.sp.base.appbase.BaseApplication;
import com.sp.base.database.AppDatabase;
import com.sp.base.database.UserProfileDetailsModel;

public class DatabaseOprationActivity extends AppCompatActivity {
    private AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_database_opration);
        mDb = BaseApplication.getInstance().getAppDatabase();

    }

    public void getDataFromDB() {
        UserProfileDetailsModel userProfileDetailsModel = mDb.getDbDAO().getUserProfileDetails();
        Log.e("db", "getDataFromDB: " + userProfileDetailsModel);
    }

    public void setDataFromDB() {
        UserProfileDetailsModel userProfileDetailsModel = new UserProfileDetailsModel();

        mDb.getDbDAO().insertUserData(userProfileDetailsModel);
        Log.e("db", "getDataFromDB: " + userProfileDetailsModel);
    }
}