package com.android16K.retrofit

import com.android16K.dataset.Gallery
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface JsonPlaceHolderApi {
    @GET("gall/api/list")
    fun getAllGallList():Call<List<Gallery>>

    @POST("gall/api/test")
    fun loginProcess(@Body hashData:HashMap<String, String>):Call<Any>
}