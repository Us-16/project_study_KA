package com.android16K.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.view_model.PagingSource
import kotlinx.coroutines.flow.Flow

class GallRepository(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi
) {
    fun getGalleryByPaging(): Flow<PagingData<Gallery>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagingSource(jsonPlaceHolderApi) }
        ).flow
    }
}