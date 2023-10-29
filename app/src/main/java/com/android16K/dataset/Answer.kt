package com.android16K.dataset

data class Answer(
    var id:Long? = null,
    var content: String,
    val createdDate: String,
    var modifiedDate: String,
    var gallery: Gallery,
    var account:Account
)