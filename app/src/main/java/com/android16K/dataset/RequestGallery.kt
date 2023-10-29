package com.android16K.dataset

import okhttp3.MultipartBody
import retrofit2.http.Part
import java.io.File


data class RequestGallery(
    val title:String,
    val content:String,
    val classify:String,
    val username:String? = ""
)
