package com.android16K.view_model

import androidx.lifecycle.ViewModel
import com.android16K.dataset.RequestAnswer
import com.android16K.dataset.gall.Gallery
import com.android16K.repository.GalleryRepository
import com.android16K.retrofit.RetrofitInstance
import retrofit2.awaitResponse

class GallDetailViewModel: ViewModel() {
    private val jsonPlaceHolderApi = RetrofitInstance.api

    suspend fun getGallery(gallId: Long): Gallery? = GalleryRepository(jsonPlaceHolderApi).getGallery(gallId)
    suspend fun getGalleryImage(gallId: Long) = GalleryRepository(jsonPlaceHolderApi).getGalleryImage(gallId)
    suspend fun getAnswer(gallId: Long) = jsonPlaceHolderApi.getAllAnswer(gallId).awaitResponse().body()
    suspend fun writeAnswer(requestAnswer: RequestAnswer) = jsonPlaceHolderApi.createAnswer(requestAnswer).awaitResponse().body()
}