package com.android16K.view_model

import androidx.lifecycle.ViewModel
import com.android16K.dataset.Account
import com.android16K.retrofit.RetrofitInstance
import retrofit2.awaitResponse

class AccountViewModel: ViewModel() {
    private val accountApi = RetrofitInstance.accountApi

    suspend fun createAccount(account:Account) = accountApi.createAccount(account).awaitResponse()

    suspend fun isUsernameDup(username:String) = accountApi.getUsernameDup(username).awaitResponse().body()
    suspend fun isTelDup(tel:String) = accountApi.getTelDup(tel).awaitResponse().body()
    suspend fun getAnswerCode(tel:String) = accountApi.getAnswerCoder(tel).awaitResponse().body()
}