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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityTestBinding
    //private lateinit var adapter: GallRecylerViewAdapter

    private val testData = mutableListOf<TestData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        mainBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initTestData()
        initRecylcerView()
    }

    private fun initRecylcerView() {
        val adapter = GallRecylerViewAdapter()
        adapter.testList = testData
        mainBinding.testRecycleView.adapter = adapter
        mainBinding.testRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private fun initTestData(){
        with(testData){
            add(TestData("test1", "content1"))
            add(TestData("test2", "content2"))
            add(TestData("test=3", "content3"))
            add(TestData("test4", "content14"))
            add(TestData("test5", "content15"))
            add(TestData("test6", "content16"))
            add(TestData("test7", "content17"))
        }
    }
}