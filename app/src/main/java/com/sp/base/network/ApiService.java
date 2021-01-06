package com.sp.base.network;


import com.google.gson.JsonElement;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

import static com.sp.base.network.NetworkingConstants.GETPOCKEMON;

public interface ApiService {

//    @POST()
//    Observable<JsonElement> GetRouteList(@Url String url, @Body GetRouteListRequest param);

    @GET(GETPOCKEMON)
    Observable<JsonElement> getDataList();
}