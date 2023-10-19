package com.android16K.dataset

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Gallery(
    @SerializedName("id") val id:Long,
    @SerializedName("account") val account:Account,
    @SerializedName("title") val title:String,
    @SerializedName("content") val content:String,
    @SerializedName("createDate") val createDate:LocalDateTime,
    @SerializedName("modifiedDate") val modifiedDate:LocalDateTime
)
