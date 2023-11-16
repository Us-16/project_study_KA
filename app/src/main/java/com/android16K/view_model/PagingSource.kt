package com.android16K.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.JsonPlaceHolderApi

class PagingSource(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi
): PagingSource<Long, Gallery>(){
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Gallery> {
        return try{
            val page = params.key ?: 1
            //val pageSize = params.loadSize //TODO(현재는 고정된 상태라 그렇고, 나중에 추가)

            val data = jsonPlaceHolderApi.getAllListSus(page)
            Log.i(TAG, "http://49.173.81.98:8080/ data: $data")

            LoadResult.Page(
                data = data.content,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = page.plus(1)
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, Gallery>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.data?.firstOrNull()?.id
        }
    }
}