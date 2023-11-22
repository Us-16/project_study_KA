package com.android16K

import com.android16K.retrofit.RetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginTest {
    @Test
    fun loginProcessTest(): Unit = runBlocking{
        val jsonPlaceHolderApi = RetrofitInstance.api
        val call = jsonPlaceHolderApi.loginProcess("test", "1234")

        val response = call.execute()

        println(response.body())
    }
}