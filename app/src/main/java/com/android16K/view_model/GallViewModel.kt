package com.android16K.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android16K.adapter.PagingRepositoryImpl
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import kotlinx.coroutines.flow.Flow

class GallViewModel: ViewModel() {
    private val retrofitInit = RetrofitInit().init()
    private val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)

    fun setPaging(): Flow<PagingData<Gallery>>{
        return PagingRepositoryImpl(jsonPlaceHolderApi).getResultList().cachedIn(viewModelScope)
    }
}