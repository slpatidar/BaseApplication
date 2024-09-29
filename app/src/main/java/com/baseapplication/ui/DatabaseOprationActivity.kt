package com.baseapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.baseapplication.BaseApplication
import com.baseapplication.R
import com.baseapplication.database.AppDatabase
import com.baseapplication.database.UserProfileDetailsModel

//@AndroidEntryPoint
class DatabaseOprationActivity() : AppCompatActivity() {
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_database_opration)
        mDb = BaseApplication.instance?.appDatabase
    }

    val dataFromDB: Unit
        get() {
            val userProfileDetailsModel: UserProfileDetailsModel? =
                mDb?.dbDAO?.userProfileDetails
            Log.e("db", "getDataFromDB: " + userProfileDetailsModel)
        }

    fun setDataFromDB() {
        val userProfileDetailsModel: UserProfileDetailsModel = UserProfileDetailsModel()
        mDb?.dbDAO?.insertUserData(userProfileDetailsModel)
        Log.e("db", "getDataFromDB: " + userProfileDetailsModel)
    }
}