package com.android16K

import com.android16K.retrofit.RetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginTest {
    @Test
    fun loginProcessTest(): Unit = runBlocking{
        val loginApi = RetrofitInstance.loginApi
        val call = loginApi.loginProcess("test", "1234")

        val response = call.execute()

        println(response.body())
    }
}