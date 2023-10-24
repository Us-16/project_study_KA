package com.android16K.dataset

import com.google.gson.annotations.SerializedName

data class Gallery(
    @SerializedName("id") val id:Long,
    @SerializedName("account") val account:Account,
    @SerializedName("title") val title:String,
    @SerializedName("content") val content:String,
    @SerializedName("createDate") val createDate:String,
    @SerializedName("modifiedDate") val modifiedDate: String?,
    @SerializedName("classify") val classify:String
)
