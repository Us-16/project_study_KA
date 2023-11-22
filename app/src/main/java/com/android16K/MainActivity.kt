package com.android16K

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android16K.activity.GalleryActivity
import com.android16K.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)

        intentListener(mainActivityBinding.toGallery, GalleryActivity::class.java)
        intentListener(mainActivityBinding.toTest, GalleryActivity::class.java)
    }

    private fun intentListener(button: Button, activityClass:Class<*>){
        button.setOnClickListener{
            val intent = Intent(this, activityClass)
            startActivity(intent)
            finish()
        }
    }
}