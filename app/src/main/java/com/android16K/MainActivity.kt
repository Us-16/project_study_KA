package com.android16K

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android16K.activity.GalleryActivity
import com.android16K.activity.TestActivity

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

        val toTestButton = findViewById<Button>(R.id.to_test)
        toTestButton.setOnClickListener{
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}