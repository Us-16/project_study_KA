package com.android16K

import com.android16K.dataset.Gallery
import com.android16K.retrofit.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.*

class RetorfitUnitTest {
    @Test
    fun getData(){
        val retrofitInitTool = RetrofitInit()
        val retrofitInit = retrofitInitTool.init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAllGallList()


        /*call.enqueue(object: Callback<List<Gallery>>{
            override fun onResponse(
                call: Call<List<Gallery>>,
                response: Response<List<Gallery>>
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

            override fun onFailure(call: Call<List<Gallery>>, t: Throwable) {
                println("onFailure: ${t.message}")
            }

        })*/
    }

    @Test
    fun newGetData() = runBlocking{
        val retrofitInitTool = RetrofitInit()
        val retrofitInit = retrofitInitTool.init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAllGallList()

        val response = call.execute()
        if (response.isSuccessful) {
            val data = response.body()
        } else {
            val httpStatusCode = response.code()
            val errorBody = response.errorBody()
        }
    }
}