package com.sp.base.appbase;

import android.app.Application;
import android.content.Context;
import android.widget.EditText;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.commonsware.cwac.saferoom.SQLCipherUtils;
import com.sp.base.R;
import com.sp.base.database.AppDatabase;

import java.io.IOException;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    private AppDatabase mDB;
    private final String DB_NAME = "app name";
    private static BaseApplication sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initAppDatabase();
    }
    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }
    private void initAppDatabase() {
        SQLCipherUtils.State state = SQLCipherUtils.getDatabaseState(this, DB_NAME);
        if (state != null) {
            if (state.equals(SQLCipherUtils.State.UNENCRYPTED)) {
                try {
                    SQLCipherUtils.encrypt(this, DB_NAME, getEditText().getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                initEncryptedDB();
            } else {
                initEncryptedDB();
            }
        } else {
            initEncryptedDB();
        }
    }

    private EditText getEditText() {
        String key = "";
        try {
            // key = CryptoHelperForLocalStorage.decryptString(getString(R.string.dphonix));
            key = getString(R.string.dbpsw);
            // LogUtil.printLog(TAG, "getEditText " + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EditText editText = new EditText(this);
        editText.setText(key);
        return editText;
    }

    private void initEncryptedDB() {
//        SafeHelperFactory factory = SafeHelperFactory.fromUser(getEditText().getText());
        this.mDB = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
//                .openHelperFactory(factory)
                //.addMigrations(DBMigration.MIGRATION_2_3)
                .build();
    }

    public AppDatabase getAppDatabase() {
        if (mDB == null)
            initAppDatabase();
        return mDB;
    }

}
