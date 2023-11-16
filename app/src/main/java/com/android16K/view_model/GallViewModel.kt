package com.android16K.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android16K.adapter.GallRepository
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import kotlinx.coroutines.flow.Flow

class GallViewModel(
    private val gallRepository: GallRepository
): ViewModel() {
    fun getContent(): Flow<PagingData<Gallery>> {
        return gallRepository.getGalleryByPaging()
            .cachedIn(viewModelScope)
    }
}