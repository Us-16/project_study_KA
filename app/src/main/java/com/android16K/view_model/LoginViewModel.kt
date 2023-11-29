package com.android16K.view_model

import com.android16K.retrofit.RetrofitInstance
import retrofit2.awaitResponse

class LoginViewModel {
    private val loginApi = RetrofitInstance.loginApi

    suspend fun loginAction(username:String, password:String) = loginApi.loginProcess(username, password).awaitResponse()
}