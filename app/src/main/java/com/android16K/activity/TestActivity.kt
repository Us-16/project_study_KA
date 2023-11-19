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
import com.android16K.adapter.GallRecylerViewAdapter
import com.android16K.databinding.ActivityTestBinding
import com.android16K.dataset.TestData
import com.android16K.dataset.gall.Gallery
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.android16K.view_model.GallViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TestActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}