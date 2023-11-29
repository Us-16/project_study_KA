package com.android16K.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android16K.R
import com.android16K.adapter.GallRecylerViewAdapter
import com.android16K.databinding.ActivityGalleryActvityBinding
import com.android16K.view_model.GallViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GalleryActivity : AppCompatActivity() {
    private lateinit var galleryActivityBinding: ActivityGalleryActvityBinding
    private val gallViewModel = GallViewModel()

    private var addButton: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        galleryActivityBinding = ActivityGalleryActvityBinding.inflate(layoutInflater)
        setContentView(galleryActivityBinding.root)

        getAndPutData()

        addButton = findViewById(R.id.gall_list_addButton)
    }

    override fun onStart() {
        super.onStart()
        addButton!!.setOnClickListener(addListener)
    }

    private val addListener:OnClickListener = OnClickListener{
        val intent = Intent(this.applicationContext, GalleryFormActivity::class.java)
        startActivity(intent)
        //finish()
    }
    
    private fun getAndPutData(){
        val adapter = GallRecylerViewAdapter()
        galleryActivityBinding.gallRecycleView.adapter = adapter
        galleryActivityBinding.gallRecycleView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            gallViewModel.setPaging().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}