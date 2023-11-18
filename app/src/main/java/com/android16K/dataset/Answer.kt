package com.android16K.dataset

import com.android16K.dataset.gall.Gallery

data class Answer(
    var id:Long? = null,
    var content: String,
    val createdDate: String,
    var modifiedDate: String,
    var gallery: Gallery,
    var account:Account
)