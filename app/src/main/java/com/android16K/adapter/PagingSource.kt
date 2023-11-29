package com.android16K.adapter

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.GallApi
import retrofit2.awaitResponse
import java.lang.Exception

class PagingSource(
    private val gallApi: GallApi
): PagingSource<Int, Gallery>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gallery> {
        return try{
            val pageIndex = params.key ?: 0
            val response = gallApi.getAllGallList(page = pageIndex.toLong()).awaitResponse().body()
            val data: List<Gallery> = response?.content ?: listOf()
            Log.i(TAG, "data: $data")

            val nextKey = if (data.isEmpty()) { null } else { pageIndex + 1 }
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextKey
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gallery>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}