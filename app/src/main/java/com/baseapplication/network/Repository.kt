package com.baseapplication.network

import com.google.gson.JsonElement
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class Repository @Inject constructor(private val apiService: ApiService) {

    fun executeGetDymmyList(page: Int, limit: Int): Observable<JsonElement>? {
        return apiService?.getDummyLIst(page, limit)
            ?.subscribeOn(Schedulers.io())  // Perform network operation on I/O thread
            ?.observeOn(AndroidSchedulers.mainThread())  // Observe result on the main thread
    }

    suspend fun callGetDataList(page: Int, limit: Int): ApiResponse {
        return withContext(Dispatchers.IO) { // Perform network call on I/O thread
            ApiResponse.loading()
            try {
                val response = apiService?.getDataList(page, limit)
                if (response != null) {
                    ApiResponse.success(response)
                } else {
                    ApiResponse.error(Throwable("No Data found"))
                }
            } catch (e: Exception) {
                ApiResponse.error(e)
            }
        }
    }
}