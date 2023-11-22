package com.android16K

import com.android16K.retrofit.JsonPlaceHolderApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

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