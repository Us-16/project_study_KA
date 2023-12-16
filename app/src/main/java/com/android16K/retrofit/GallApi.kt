package com.android16K.retrofit

import com.android16K.dataset.Answer
import com.android16K.dataset.RequestAnswer
import com.android16K.dataset.gall.Gallery
import com.android16K.dataset.gall.GalleryImage
import com.android16K.dataset.gall.GalleryResponse
import com.android16K.dataset.gall.RequestGallery
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface GallApi {
    @GET("gall/api/list")
    fun getAllGallList(@Query("page")page:Long? = 0): Call<GalleryResponse>
    @GET("/gall/api/detail")
    fun getGallItem(@Query("id") gallId: Long): Call<Gallery>
    @GET("/gall/api/image-list")
    fun getGallImageList(@Query("id") gallId:Long): Call<List<GalleryImage>>

    @Multipart
    @POST("/gall/api/create-image/{gallId}")
    fun createImage(@Part image: MultipartBody.Part, @Path(value = "gallId") gallId: Long): Call<Any>
    @POST("/gall/api/create")
    fun createGall(@Body requestGallery: RequestGallery): Call<Gallery>

    @GET("/gall/api/answer-list")
    fun getAllAnswer(@Query("gallId") gallId: Long): Call<List<Answer>>
    @POST("/gall/api/create/answer")
    fun createAnswer(@Body requestAnswer: RequestAnswer): Call<Long>
    @DELETE("/gall/api/delete")
    fun deleteGallery(@Query("gallId")gallId: Long): Call<Void>
    @PUT("/gall/api/update")
    fun updateGallery(@Body requestGallery: RequestGallery): Call<Gallery>
}