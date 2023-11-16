package com.android16K

import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.android16K.view_model.GallViewModel
import org.junit.Test

class GalleryResponseTest {
    private val gallViewModel = GallViewModel()
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