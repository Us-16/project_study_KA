package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.android16K.R
import com.android16K.view_model.GallDetailViewModel
import kotlinx.coroutines.launch

class TestActivity: AppCompatActivity() {
    private val gallDetailViewModel = GallDetailViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        test()
    }

    private fun test(){
        lifecycleScope.launch {
            Log.i(TAG, "test: ${gallDetailViewModel.getGallery(1009L)}")
        }
    }
}