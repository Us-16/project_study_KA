package com.android16K.dataset.gall

import androidx.recyclerview.widget.DiffUtil
import com.android16K.dataset.Account
import com.google.gson.annotations.SerializedName

data class Gallery(
    @SerializedName("id") var id:Long,
    @SerializedName("account") var account: Account,
    @SerializedName("title") var title:String,
    @SerializedName("content") var content:String,
    @SerializedName("createdDate") var createDate:String,
    @SerializedName("modifiedDate") var modifiedDate: String?,
    @SerializedName("classify") var classify:String
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
