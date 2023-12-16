package com.android16K.dataset.gall



data class RequestGallery(
    val id:Long? = null,
    val title:String,
    val content:String,
    val classify:String? = "",
    val username:String? = ""
)
