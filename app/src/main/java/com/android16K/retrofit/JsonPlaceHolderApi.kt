package com.android16K.retrofit

import com.android16K.dataset.Gallery
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceHolderApi {
    @GET("gall/api/list")
    fun getAllGallList():Call<List<Gallery>>
}