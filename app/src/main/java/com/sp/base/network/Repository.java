package com.sp.base.network;

import com.google.gson.JsonElement;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class Repository {

    private ApiService apiService;

    @Inject
    public Repository(ApiService apiService) {
        this.apiService = apiService;
    }

//    public Observable<JsonElement> executeGetRouteListAPI(GetRouteListRequest getRouteListRequest) {
//        return apiService.GetRouteList(NetworkingConstants.BASE_URL + NetworkingConstants.ROUTES_LIST, getRouteListRequest);
//    }

    public Observable<JsonElement> executeGetDataList() {
        return apiService.getDataList();
    }


}