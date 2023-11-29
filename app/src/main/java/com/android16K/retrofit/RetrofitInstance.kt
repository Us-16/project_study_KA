package com.android16K.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofitInit by lazy {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        Retrofit.Builder()
            .baseUrl("http://49.173.81.98:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }

    val gallApi:GallApi by lazy { retrofitInit.create(GallApi::class.java) }
    val loginApi: LoginApi by lazy { retrofitInit.create(LoginApi::class.java) }
    val accountApi:AccountApi by lazy { retrofitInit.create(AccountApi::class.java) }
}