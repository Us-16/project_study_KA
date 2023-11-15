package com.android16K

import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import org.junit.Test

class GalleryResponseTest {
    @Test
    fun getData(){
        val retrofitInitTool = RetrofitInit()
        val retrofitInit = retrofitInitTool.init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAllGallList()

        val response = call.execute()
        println(response.body())
    }
}