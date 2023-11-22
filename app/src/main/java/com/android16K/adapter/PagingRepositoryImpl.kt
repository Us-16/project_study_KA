package com.android16K.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.GallApi
import kotlinx.coroutines.flow.Flow

class PagingRepositoryImpl(
    private val gallApi: GallApi
){
    fun getResultList(): Flow<PagingData<Gallery>> {
        return Pager(PagingConfig(pageSize = 20)){
            PagingSource(gallApi)
        }.flow
    }
}