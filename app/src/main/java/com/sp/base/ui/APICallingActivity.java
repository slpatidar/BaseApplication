package com.sp.base.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.sp.base.R;
import com.sp.base.countrypicker.Country;
import com.sp.base.countrypicker.CountryPickerCallbacks;
import com.sp.base.countrypicker.CountryPickerDialog;
import com.sp.base.databinding.ActivityMainBinding;
import com.sp.base.network.ApiResponse;
import com.sp.base.ui.model.DummyResponseModel;
import com.sp.base.ui.model.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class APICallingActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        observeData();
        callAPI();
    }

    public void getCountryPicker(Context mContext) {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(mContext, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country, int flagResId) {
                        Log.e("selected date", "onCountrySelected: " + country.getDialingCode());
                    }
                });
        countryPicker.show();
    }

    private void callAPI() {
        viewModel.getDataList();
    }

    private void observeData() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getResponse().observe(this, new androidx.lifecycle.Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                consumeResponse(apiResponse, "list");
            }
        });
    }


    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {
            case LOADING:
//                mBinding.progressIndicator.show();
                mBinding.result.setText("");

                break;

            case SUCCESS:
//                mBinding.progressIndicator.hide();
                if (!apiResponse.data.isJsonNull()) {
                    Log.e("response:", apiResponse.data.toString());
                    if (tag.equalsIgnoreCase("list")) {
                        DummyResponseModel dummyResponseModel = new Gson().fromJson(apiResponse.data.toString(), DummyResponseModel.class);
                        Log.e("response", "consumeResponse: " + dummyResponseModel.toString());
                        mBinding.result.setText("Result: " + dummyResponseModel.results.toString());
                    }
                }

                break;
            case ERROR:
                mBinding.result.setText("");
//                mBinding.progressIndicator.hide();
                break;
            default:
//                mBinding.progressIndicator.hide();
                mBinding.result.setText("");

                break;
        }
    }
}