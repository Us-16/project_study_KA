package com.android16K.dataset.gall

import androidx.recyclerview.widget.DiffUtil
import com.android16K.dataset.Account
import com.google.gson.annotations.SerializedName

data class Gallery(
    @SerializedName("id") val id:Long,
    @SerializedName("account") val account: Account,
    @SerializedName("title") val title:String,
    @SerializedName("content") val content:String,
    @SerializedName("createdDate") val createDate:String,
    @SerializedName("modifiedDate") val modifiedDate: String?,
    @SerializedName("classify") val classify:String
){
    companion object{
        val DiffCallBack = object:DiffUtil.ItemCallback<Gallery>(){
            override fun areItemsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
                return oldItem == newItem
            }
        }
    }
}
