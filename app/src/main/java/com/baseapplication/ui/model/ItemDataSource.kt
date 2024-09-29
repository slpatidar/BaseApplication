package com.onetuchservice.business.ui.model

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.baseapplication.network.Repository
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

internal class ItemDataSource(var repository: Repository) : PageKeyedDataSource<Int, ListData>() {
    override fun loadAfter(
        loadParams: LoadParams<Int>,
        loadCallback: LoadCallback<Int, ListData>
    ) {
        Log.e(TAG, "loadAfter: ")
        repository.executeGetDymmyList(loadParams.key, PAGE_SIZE)!!.subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe { jsonElement -> //                Integer key = (loadParams.key == loadParams.body().getTotalResults()) ? null : params.key + 1;
                val dummyResponse =
                    Gson().fromJson(jsonElement.toString(), DummyResponse::class.java)
                dummyResponse.results?.let { loadCallback.onResult(it, loadParams.key + 1) }
                Log.e(TAG, "accept: ")
            }


//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
//                .subscribe(
//                        result ->                             loadCallback.onResult(ApiResponse.success().body().getArticles(), null, ArticleMovieConstants.FIRST_PAGE + 1);
//,
//                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
//                );
    }

    override fun loadBefore(
        loadParams: LoadParams<Int>,
        loadCallback: LoadCallback<Int, ListData>
    ) {
        Log.e(TAG, "loadBefore: ")
    }

    override fun loadInitial(
        loadInitialParams: LoadInitialParams<Int>,
        loadInitialCallback: LoadInitialCallback<Int, ListData>
    ) {
        Log.e(TAG, "loadInitial: ")
        repository.executeGetDymmyList(FIRST_PAGE, PAGE_SIZE)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { apiResponse ->
                    val dummyResponse =
                        Gson().fromJson(apiResponse.toString(), DummyResponse::class.java)
                dummyResponse.results?.let { loadInitialCallback.onResult(it, null, FIRST_PAGE) }
                    Log.e(TAG, "accept: ")
                })
    }

    companion object {
        val PAGE_SIZE = 50
        val FIRST_PAGE = 0
        private val TAG = ItemDataSource::class.java.simpleName
    }
}
