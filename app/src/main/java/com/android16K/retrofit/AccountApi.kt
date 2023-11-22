package com.android16K.retrofit

import com.android16K.dataset.Account
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountApi {
    @GET("/account/api/username-dup")
    fun getUsernameDup(@Query("username") username: String): Call<Boolean>
    @GET("/sms/tel-dup")
    fun getTelDup(@Query("tel") tel: String): Call<Boolean>
    @GET("/sms/code")
    fun getAnswerCoder(@Query("tel") tel: String): Call<String>
    @POST("/account/api/create")
    fun createAccount(@Body account: Account): Call<Boolean>
}