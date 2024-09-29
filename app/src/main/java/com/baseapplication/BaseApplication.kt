package com.baseapplication

import android.content.Context
import android.widget.EditText
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.baseapplication.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : MultiDexApplication() {
    private var mDB: AppDatabase? = null
    private val DB_NAME = "app name"

    override fun onCreate() {
        super.onCreate()
        instance = this
        Companion.context = applicationContext
//        initAppDatabase()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this@BaseApplication)
    }


    val context: Context?
        get() {
            if (Companion.context == null) {
                Companion.context = applicationContext
            }
            return Companion.context
        }
//
    private fun initAppDatabase() {
//        SQLCipherUtils.State state = SQLCipherUtils.getDatabaseState(this, DB_NAME);
//        if (state != null) {
//            if (state.equals(SQLCipherUtils.State.UNENCRYPTED)) {
//                try {
//                    SQLCipherUtils.encrypt(this, DB_NAME, getEditText().getText());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                initEncryptedDB();
//            } else {
//                initEncryptedDB();
//            }
//        } else {
        initEncryptedDB()
        //        }
    }
//
//    private val editText: EditText
//        private get() {
//            var key = ""
//            try {
//                // key = CryptoHelperForLocalStorage.decryptString(getString(R.string.dphonix));
//                key = getString(R.string.dbpsw)
//                // LogUtil.printLog(TAG, "getEditText " + key);
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            val editText = EditText(this)
//            editText.setText(key)
//            return editText
//        }
//
    private fun initEncryptedDB() {
//        SafeHelperFactory factory = SafeHelperFactory.fromUser(getEditText().getText());
        mDB = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, DB_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration() //                .openHelperFactory(factory)
            //.addMigrations(DBMigration.MIGRATION_2_3)
            .build()
    }
//
    val appDatabase: AppDatabase?
        get() {
            if (mDB == null) initAppDatabase()
            return mDB
        }

    companion object {
        /**
         * @return ApplicationController singleton instance
         */
        @get:Synchronized
        var instance: BaseApplication? = null
            private set
        private var context: Context? = null
    }
}