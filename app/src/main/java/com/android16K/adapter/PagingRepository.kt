package com.android16K.adapter

import androidx.paging.PagingData
import com.android16K.dataset.gall.Gallery
import kotlinx.coroutines.flow.Flow

interface PagingRepository {
    fun getResultList(): Flow<PagingData<Gallery>>
}