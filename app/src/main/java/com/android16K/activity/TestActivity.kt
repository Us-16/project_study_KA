package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android16K.R
import com.android16K.adapter.GallAdapter
import com.android16K.adapter.GallRepository
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.android16K.view_model.GallViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {
    private val retrofitInit = RetrofitInit().init()
    private val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
    private val gallRepository = GallRepository(jsonPlaceHolderApi)

    private val gallViewModel = GallViewModel(gallRepository)

    private val adapter = GallAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val recyclerView = findViewById<RecyclerView>(R.id.test_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //Log.i(TAG, "http://49.173.81.98:8080 data-VM: ${gallViewModel.pagingData}")

        lifecycleScope.launch {
            Log.i(TAG, "http://49.173.81.98:8080 Test: ${gallViewModel.getContent()}")
            gallViewModel.getContent().collectLatest { data ->
                data.map {
                    Log.i(TAG, "http://49.173.81.98:8080 Collect: ${it.title}")
                }
                adapter.submitData(data)
            }
        }
    }
}