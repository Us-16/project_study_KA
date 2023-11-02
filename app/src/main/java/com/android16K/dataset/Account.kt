package com.android16K.dataset

data class Account(
    var id:Long? = null,
    val username:String,
    val password:String,
    val name:String,
    val tel:String,
    val role:String,
    val createDate:String,
    val modifiedDate: String? = null //시간값 문제
)
