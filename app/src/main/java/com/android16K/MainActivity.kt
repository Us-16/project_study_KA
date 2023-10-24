package com.android16K

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android16K.activity.GalleryActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toGallButton = findViewById<Button>(R.id.to_gallery)

        toGallButton.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}