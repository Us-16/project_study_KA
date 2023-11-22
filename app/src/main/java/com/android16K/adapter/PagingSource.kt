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
            // 최초 요청 페이지
            val pageIndex = params.key ?: 0
            // Api 호출 결과 리스트
            val response =
                gallApi.getAllGallList(
                    page = pageIndex.toLong()
                ).awaitResponse().body()
            // 검색 리스트
            val data: List<Gallery> = response?.content ?: listOf()
            Log.i(TAG, "data: $data")

            // 페이지 넘버값 증가
            val nextKey =
                // 마지막 페이지, 데이터 여부 확인
                if (data.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            // 페이징 처리
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