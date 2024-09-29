package com.onetuchservice.business.ui.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.baseapplication.network.ApiResponse
import com.baseapplication.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var repository: Repository) : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    // PagedList configuration
    private val config = PagedList.Config.Builder()
        .setPageSize(20) // Page size (the number of items to load per page)
        .setEnablePlaceholders(false)
        .build()
    // LiveData to observe the API result
    private val _responseLiveData = MutableLiveData<ApiResponse?>()
    val responseLiveData get() = _responseLiveData

    //using kotlin coroutins
    fun getData(page: Int, limit: Int) {
       viewModelScope.launch {
           val response =   repository.callGetDataList(page, limit)
           _responseLiveData.value = response
       }
    }

    //using RxJava
        fun getDataList(page: Int, limit: Int) {
        repository.executeGetDymmyList(page, limit)?.doOnSubscribe {
            responseLiveData.setValue(ApiResponse.loading())
        }?.subscribe({ result ->
            // On Success
            responseLiveData.setValue(ApiResponse.success(result))
        }, { throwable ->
            // On Error
            responseLiveData.setValue(ApiResponse.error(throwable))
        })
    }

}


