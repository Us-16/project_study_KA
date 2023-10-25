package com.android16K.dataset

import retrofit2.http.Field

data class LoginAccount(
    @Field("username") val username:String,
    @Field("password") val password:String
) {
}