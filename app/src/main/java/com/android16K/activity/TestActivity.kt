package com.android16K.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android16K.databinding.ActivityTestBinding
import com.android16K.view_model.GallDetailViewModel

class TestActivity: AppCompatActivity() {
    private val gallDetailViewModel = GallDetailViewModel()
    private lateinit var testBinding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        testBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(testBinding.root)
    }
}
