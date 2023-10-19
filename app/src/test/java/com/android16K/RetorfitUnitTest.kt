package com.android16K

import android.content.ContentValues.TAG
import android.util.Log
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetorfitUnitTest {
    @Test
    fun getData(){
        val retrofitInitTool = RetrofitInit()
        val retrofitInit = retrofitInitTool.init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAllGallList()
        println("test")
        println("${call.request()}")


        call.enqueue(object: Callback<HashMap<String, String>>{
            override fun onResponse(
                call: Call<HashMap<String, String>>,
                response: Response<HashMap<String, String>>
            ) {
                println("isIt?: ${response.isSuccessful}")
                if(response.isSuccessful){
                    val data = response.body()
                    println("onResponse: $data")
                }else{
                    val httpStatusCode = response.code()
                    val errorBody = response.errorBody()
                    println("onResponse: $httpStatusCode")
                    println("onResponse: $errorBody")
                }
            }

            override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                println("onFailure: ${t.message}")
            }

        })
    }
}