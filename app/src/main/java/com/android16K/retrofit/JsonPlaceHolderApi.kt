package com.android16K.retrofit

import com.android16K.dataset.Answer
import com.android16K.dataset.Gallery
import com.android16K.dataset.GalleryImage
import com.android16K.dataset.LoginAccount
import com.android16K.dataset.RequestAnswer
import com.android16K.dataset.RequestGallery
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface JsonPlaceHolderApi {
    @GET("gall/api/list")
    fun getAllGallList():Call<List<Gallery>>

    @FormUrlEncoded
    @POST("login/action")
    fun loginProcess(@Field("username")username:String, @Field("password")password:String):Call<HashMap<String, Any>>
    @Multipart
    @POST("/gall/api/create-image")
    fun createImage(@Part image:MultipartBody.Part): Call<Any>
    @POST("/gall/api/create")
    fun createGall(@Body requestGallery: RequestGallery): Call<Gallery>

    @GET("/gall/api/detail")
    fun getGallItem(@Query("id") gallId: Long): Call<Gallery>

    @GET("/gall/api/image-list")
    fun getGallImageList(@Query("id") gallId:Long): Call<List<GalleryImage>>
    @GET("/gall/api/answer-list")
    fun getAllAnswer(@Query("gallId") gallId: Long): Call<List<Answer>>
    @POST("/gall/api/create/answer")
    fun createAnswer(@Body requestAnswer: RequestAnswer): Call<Long>
}