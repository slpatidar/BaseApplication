package com.onetuchservice.business.ui.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.baseapplication.network.Repository


class ItemDataSourceFactory(var repository: Repository) : DataSource.Factory<Int, ListData>() {
    private val itemMutableLiveData: MutableLiveData<PageKeyedDataSource<Int, ListData>> =
        MutableLiveData()

    public override fun create(): DataSource<Int, ListData> {
        Log.e(TAG, "create: ")
        //getting our data source object
        val articleDataSource: ItemDataSource = ItemDataSource(repository)
        //posting the data source to get the values
        itemMutableLiveData.postValue(articleDataSource)
        return articleDataSource
    }

    val itemLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, ListData>>
        //getter for itemlivedatasource
        get() {
            Log.e(TAG, "getItemLiveDataSource: ")
            return itemMutableLiveData
        }

    companion object {
        private val TAG: String = ItemDataSourceFactory::class.java.getSimpleName()
    }
}
