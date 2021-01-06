package com.sp.base.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

import static com.sp.base.network.NetworkingConstants.BASE_URL;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {

    private static final String TAG = NetworkModule.class.getSimpleName();
    private static final String contentType = "application/json";
    private static final String accept = "application/json";
    private String mHostName = "";

    @Provides
    @Singleton
    @Inject
    @Named("provideRetrofit2")
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    @Inject
    @Named("provideRetrofit2")
    Retrofit provideRetrofit(@Named("provideRetrofit2") Gson gson, @Named("provideRetrofit2") OkHttpClient okHttpClient) {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        builder.client(okHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        return builder.build();
    }

    /**
     * set ssl certificate at dynamic from real time database (fire-base)
     * there is two way to set ssl pinning in dynamic
     * 1. when app is lunch at that time, call fire-base method for getting update ssl pin
     * 2. when getting error in any api at that time also update the ssl pin
     *
     * @return
     */
    @Provides
    @Singleton
    ApiService ApiCallInterface(@Named("provideRetrofit2") Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    @Named("provideRetrofit2")
    OkHttpClient getRequestHeader() {
        try {
            URI uri = new URI(BASE_URL);
            mHostName = uri.getHost();
            Log.e(TAG, mHostName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        if (LogUtil.isEnableLogs) { //dont show logs from here
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            httpClient.addInterceptor(interceptor);
//        }

        try {
            httpClient.addInterceptor(new Interceptor() {

                @NonNull
                @EverythingIsNonNull
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request;
                    Response response;

                    String url = original.url().toString();
                    Request.Builder requestBuilder = original.newBuilder();
                    requestBuilder.addHeader("Content-Type", contentType);
                    requestBuilder.addHeader("Accept", accept);
//                    requestBuilder.addHeader("Authorization", "Bearer " + BaseApplication.FB_TOKEN);
                    requestBuilder.method(original.method(), original.body());
                    request = requestBuilder.build();
                    response = chain.proceed(request);

                    return response;
                }
            })
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpClient.build();
    }
//
//    @Provides
//    @Singleton
//    Service getRepository(NetworkAPIServices networkAPIServices) {
//        return new Service(networkAPIServices);
//    }
//
//    @Provides
//    @Singleton
//    ViewModelProvider.Factory getViewModelFactory(Service myService) {
//        return new ViewModelFactory(myService);
//    }
}