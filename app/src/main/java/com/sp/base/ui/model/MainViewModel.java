package com.sp.base.ui.model;


import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.sp.base.network.ApiResponse;
import com.sp.base.network.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainViewModel extends ViewModel {
    public Repository repository;
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    @ViewModelInject
    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> getResponse() {
        return responseLiveData;
    }

//    @SuppressLint("CheckResult")
//    public void getRouteList(GetRouteListRequest getRouteListRequest) {
//        repository.executeGetRouteListAPI(getRouteListRequest)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
//                .subscribe(
//                        result -> responseLiveData.setValue(ApiResponse.success(result)),
//                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
//                );
//
//    }

    public void getDataList() {
        repository.executeGetDataList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                );
    }

}