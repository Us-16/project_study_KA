package com.android16K.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val retrofitInitial by lazy {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        Retrofit.Builder()
            .baseUrl("http://49.173.81.98:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }

    val api: JsonPlaceHolderApi by lazy {
        retrofitInitial.create(JsonPlaceHolderApi::class.java)
    }

    val loginApi: LoginApi by lazy {
        retrofitInitial.create(LoginApi::class.java)
    }
}