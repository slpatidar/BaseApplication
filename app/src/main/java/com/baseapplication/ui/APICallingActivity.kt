package com.baseapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.baseapplication.R
import com.baseapplication.databinding.ActivityMainBinding
import com.baseapplication.network.ApiResponse
import com.baseapplication.network.Status
import com.baseapplication.ui.adapter.ListDataAdapter
import com.google.gson.Gson
import com.onetuchservice.business.ui.model.DummyResponse
import com.onetuchservice.business.ui.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class APICallingActivity() : AppCompatActivity() {
    private val TAG: String = APICallingActivity::class.java.getSimpleName()

    lateinit var mBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    var allNotificationListAdapter: ListDataAdapter? = null


    // PagedList configuration
    private val config = PagedList.Config.Builder()
        .setPageSize(20) // Page size (the number of items to load per page)
        .setEnablePlaceholders(false)
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initData()
        observeData()
        viewModel?.getData(1, 20)

    }

    private fun initData() {
        val manager: LinearLayoutManager = LinearLayoutManager(this,VERTICAL,false)
        mBinding?.rv?.layoutManager = manager
        mBinding?.rv?.setHasFixedSize(true)
        allNotificationListAdapter = ListDataAdapter(this@APICallingActivity)
        mBinding?.rv?.adapter = allNotificationListAdapter
    }

    private fun observeData() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel?.responseLiveData?.observe(this) { apiResponse ->
            apiResponse?.let {
                consumeResponse(it, "list")
            }
        }
    }

    private fun consumeResponse(apiResponse: ApiResponse, tag: String) {

        Log.e("response:", apiResponse.data.toString())
        when (apiResponse.status) {
            Status.LOADING ->
                mBinding.progressIndicator.show();
            Status.SUCCESS -> {
                mBinding.progressIndicator.hide();
                if (apiResponse.data?.isJsonNull == false) {
                    if (tag.equals("list", ignoreCase = true)) {
                        val dummyResponse: DummyResponse = Gson().fromJson(apiResponse.data.toString(), DummyResponse::class.java)
//                        allNotificationListAdapter = AllNotificationListAdapter(this@APICallingActivity)
//                        mBinding?.rv?.adapter = allNotificationListAdapter
                        // DataSourceFactory from repository or data source

//                        val listData = LivePagedListBuilder(dummyResponse.results, config).build()
//                        allNotificationListAdapter?.submitList(dummyResponse.results)
                    }
                }
            }

            Status.ERROR -> Log.e(TAG, "consumeResponse error: " + apiResponse.error)

            else -> {
                Log.e(TAG, "consumeResponse else: $apiResponse")
            }
        }
    }

}