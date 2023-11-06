package com.android16K.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import com.android16K.R
import com.android16K.adapter.GalleryAdapter
import com.android16K.dataset.Gallery
import com.android16K.retrofit.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.*

class GalleryActivity : AppCompatActivity() {
    private var addButton: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_actvity)

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
        val retrofitInit = RetrofitInit().init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAllGallList()

        call.enqueue(object : Callback<List<Gallery>> {
            override fun onResponse(call: Call<List<Gallery>>, response: Response<List<Gallery>>) {
                if (response.isSuccessful){
                    val data = response.body()
                    val galleryAdapter = GalleryAdapter(this@GalleryActivity, data)
                    val gallListView = findViewById<ListView>(R.id.gall_list)
                    gallListView.onItemClickListener =
                        OnItemClickListener { parent, view, position, id ->
                            val it = Intent(this@GalleryActivity.applicationContext, GalleryDetailActivity::class.java)
                            it.putExtra("gall_id", data?.get(position)?.id)
                            startActivity(it)
                        }
                    gallListView.adapter = galleryAdapter
                }else{
                    Log.e(TAG, "onResponse: ${response.code()}")
                    Log.e(TAG, "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Gallery>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}