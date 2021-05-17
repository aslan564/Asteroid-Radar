package com.udacity.asteroidradar.api

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.service.AsteroidPictureService
import com.udacity.asteroidradar.util.Constants
import com.udacity.asteroidradar.api.service.AsteroidService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
   // private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    private val okHttpClientBuilder = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null
    private const val API_KEY = "Your api key"
    private val requestInterceptor = Interceptor { chain ->

        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    private fun okHttpClient(): OkHttpClient {
        when {
            BuildConfig.DEBUG -> {

                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(logging)
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
            }
        }
        return okHttpClientBuilder.build()
    }

    private fun getClient(): Retrofit {
        when (retrofit) {
            null -> {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(okHttpClient())
                    .baseUrl(Constants.BASE_URL)
                    .build()
                Log.d("retrofitSVC", "getClient: $retrofit")
            }
        }
        Log.d("retrofitSVC", "getClient: ${retrofit.toString()}")
        return retrofit as Retrofit
    }

    val asteroidService: AsteroidService by lazy { getClient().create(AsteroidService::class.java) }

    val asteroidImageService: AsteroidPictureService by lazy {
        getClient().create(
            AsteroidPictureService::class.java
        )
    }

}