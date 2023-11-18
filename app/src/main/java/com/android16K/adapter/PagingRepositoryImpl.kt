package com.android16K.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.JsonPlaceHolderApi
import kotlinx.coroutines.flow.Flow

class PagingRepositoryImpl(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi
): PagingRepository {
    override fun getResultList(): Flow<PagingData<Gallery>> {
        return Pager(PagingConfig(pageSize = 20)){
            PagingSource(jsonPlaceHolderApi)
        }.flow
    }
}