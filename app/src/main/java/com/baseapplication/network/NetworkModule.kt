package com.baseapplication.network

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.internal.EverythingIsNonNull
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule() {
    private var mHostName: String = ""
    @Provides
    @Singleton
    @Inject
    @Named("provideRetrofit2")
    fun provideGson(): Gson {
        val builder: GsonBuilder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    @Inject
    @Named("provideRetrofit2")
    fun provideRetrofit(
        @Named("provideRetrofit2") gson: Gson?,
        @Named("provideRetrofit2") okHttpClient: OkHttpClient?
    ): Retrofit {
        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl(NetworkingConstants.Companion.BASE_URL)
        builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        builder.client(okHttpClient)
        builder.addConverterFactory(GsonConverterFactory.create(gson))
        return builder.build()
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
    fun ApiCallInterface(@Named("provideRetrofit2") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @get:Named("provideRetrofit2")
    @get:Singleton
    @get:Provides
    val requestHeader: OkHttpClient
        get() {
            try {
                val uri: URI = URI(NetworkingConstants.Companion.BASE_URL)
                mHostName = uri.getHost()
                Log.e(TAG, mHostName)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            val httpClient:  OkHttpClient.Builder =  OkHttpClient.Builder()
            //        if (LogUtil.isEnableLogs) { //dont show logs from here
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            httpClient.addInterceptor(interceptor);
//        }
            try {
                httpClient.addInterceptor(object : Interceptor {
                    @EverythingIsNonNull
                    @Throws(IOException::class)
                    public override fun intercept(chain: Interceptor.Chain): Response {
                        val original: Request = chain.request()
                        val request: Request
                        val response: Response
                        val url: String = original.url.toString()
                        val requestBuilder: Request.Builder = original.newBuilder()
                        requestBuilder.addHeader("Content-Type", contentType)
                        requestBuilder.addHeader("Accept", accept)
                        requestBuilder.addHeader("app-id", NetworkingConstants.Companion.APIID)
                        //                    requestBuilder.addHeader("Authorization", "Bearer " + BaseApplication.FB_TOKEN);
                        requestBuilder.method(original.method, original.body)
                        request = requestBuilder.build()
                        response = chain.proceed(request)
                        return response
                    }
                })
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return httpClient.build()
        } //

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
    companion object {
        private val TAG: String = NetworkModule::class.java.getSimpleName()
        private val contentType: String = "application/json"
        private val accept: String = "application/json"
    }
}