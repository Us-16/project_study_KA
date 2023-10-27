package com.android16K.retrofit

import com.android16K.dataset.Gallery
import com.android16K.dataset.LoginAccount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface JsonPlaceHolderApi {
    @GET("gall/api/list")
    fun getAllGallList():Call<List<Gallery>>

    /**
     * 현 시점에서는 이 방식이 최선이라 이대로 진행
     * 다만, 반환값이 html 그대로 나와서 call 객체 단계에서 애러 발생
     */
    @FormUrlEncoded
    @POST("login/action")
    fun loginProcess(@Field("username")username:String, @Field("password")password:String):Call<HashMap<String, Any>>
}