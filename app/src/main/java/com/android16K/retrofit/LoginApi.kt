package com.android16K.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

sealed interface LoginApi{
    @FormUrlEncoded
    @POST("login/action")
    fun loginProcess(@Field("username")username:String, @Field("password")password:String): Call<HashMap<String, Any>>
}