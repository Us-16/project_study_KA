package com.android16K.repository

import com.android16K.dataset.gall.Gallery
import com.android16K.dataset.gall.GalleryImage
import com.android16K.retrofit.JsonPlaceHolderApi
import retrofit2.awaitResponse

class GalleryRepository(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi
) {
    suspend fun getGallery(gallId:Long): Gallery? {
         return jsonPlaceHolderApi.getGallItem(gallId).awaitResponse().body()
    }

    suspend fun getGalleryImage(gallId:Long): List<GalleryImage>? {
        return jsonPlaceHolderApi.getGallImageList(gallId).awaitResponse().body()
    }
}