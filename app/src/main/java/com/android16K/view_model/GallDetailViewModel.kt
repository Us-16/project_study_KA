package com.android16K.view_model

import androidx.lifecycle.ViewModel
import com.android16K.dataset.RequestAnswer
import com.android16K.dataset.gall.Gallery
import com.android16K.dataset.gall.GalleryImage
import com.android16K.dataset.gall.RequestGallery
import com.android16K.retrofit.RetrofitInstance
import okhttp3.MultipartBody
import retrofit2.awaitResponse

class GallDetailViewModel: ViewModel() {
    private val gallApi = RetrofitInstance.gallApi

    suspend fun getGallery(gallId: Long): Gallery? = gallApi.getGallItem(gallId).awaitResponse().body()
    suspend fun getGalleryImage(gallId: Long): List<GalleryImage>? = gallApi.getGallImageList(gallId).awaitResponse().body()
    suspend fun getAnswer(gallId: Long) = gallApi.getAllAnswer(gallId).awaitResponse().body()
    suspend fun writeAnswer(requestAnswer: RequestAnswer) = gallApi.createAnswer(requestAnswer).awaitResponse().body()

    suspend fun sendForm(requestGallery: RequestGallery) = gallApi.createGall(requestGallery).awaitResponse()
    suspend fun uploadImage(image:MultipartBody.Part, gallId: Long) = gallApi.createImage(image, gallId).awaitResponse()
    suspend fun deleteGall(gallId: Long) = gallApi.deleteGallery(gallId).awaitResponse()

    suspend fun updateGall(requestGallery: RequestGallery) = gallApi.updateGallery(requestGallery).awaitResponse()
}