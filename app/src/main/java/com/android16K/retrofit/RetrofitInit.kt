package com.android16K.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInit {
    fun init(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                val responseBody = response.body()
                val responseBodyString = responseBody?.string() ?: ""

                response.newBuilder().body(
                    ResponseBody.create(responseBody?.contentType(), responseBodyString)
                ).build()
            }.build()


        return Retrofit.Builder()
            .baseUrl("http://192.168.0.11:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}