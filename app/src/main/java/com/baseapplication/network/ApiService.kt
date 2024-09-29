package com.baseapplication.network

import com.google.gson.JsonElement
import com.baseapplication.network.NetworkingConstants.Companion.DUMMY
import com.baseapplication.network.NetworkingConstants.Companion.GETPOCKEMON
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

open interface ApiService {
    @get:GET(GETPOCKEMON)
    val dataList: Observable<JsonElement>?

    @GET(DUMMY)
    fun getDummyLIst(
        @Query("page") page: Int,
        @Query("limit") lmit: Int
    ): Observable<JsonElement>? //page=1&limit=10"

     @GET(DUMMY)
    suspend fun getDataList(
        @Query("page") page: Int,
        @Query("limit") lmit: Int
    ): JsonElement? //page=1&limit=10"
}