package com.lomboktengahkab.wisataapp.network


import com.lomboktengahkab.wisataapp.Constants.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {

    private var instance : Retrofit? = null

    private fun provideOkHttpClient() : OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                )
            )
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }


    @Synchronized
    private fun getRetrofit() : Retrofit{
        if (instance == null){
            instance = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .baseUrl(BASE_URL)
                .build()
        }
        return instance as Retrofit
    }

    fun service() : ApiServices = getRetrofit().create(ApiServices::class.java)


}