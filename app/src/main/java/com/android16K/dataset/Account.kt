package com.android16K.dataset

import java.time.LocalDateTime

data class Account(
    val id:Long,
    val username:String,
    val password:String,
    val name:String,
    val tel:String,
    val role:String,
    val createDate:LocalDateTime,
    val modifiedDate:LocalDateTime
)