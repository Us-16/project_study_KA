package com.android16K

import com.android16K.dataset.LoginAccount
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginTest {
    @Test
    fun loginProcessTest(): Unit = runBlocking{
        val retrofitInit = RetrofitInit().init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.loginProcess("test", "1234")

        val response = call.execute()

        println(response.body())
    }
}