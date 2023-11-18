package com.android16K.retrofit

import com.android16K.dataset.*
import com.android16K.dataset.gall.Gallery
import com.android16K.dataset.gall.GalleryImage
import com.android16K.dataset.gall.GalleryResponse
import com.android16K.dataset.gall.RequestGallery
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {
    @GET("gall/api/list")
    fun getAllGallList(@Query("page")page:Long? = 0): Call<GalleryResponse>

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

    @GET("/account/api/username-dup")
    fun getUsernameDup(@Query("username") username: String): Call<Boolean>
    @GET("/sms/tel-dup")
    fun getTelDup(@Query("tel") tel: String): Call<Boolean>
    @GET("/sms/code")
    fun getAnswerCoder(@Query("tel") tel: String): Call<String>
    @POST("/account/api/create")
    fun createAccount(@Body account: Account): Call<Boolean>
}