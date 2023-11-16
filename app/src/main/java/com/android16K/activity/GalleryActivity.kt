package com.android16K.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ListView
import com.android16K.R
import com.android16K.adapter.GalleryAdapter
import com.android16K.dataset.gall.GalleryResponse
import com.android16K.retrofit.*
import com.android16K.view_model.GallViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryActivity : AppCompatActivity() {
    private var addButton: FloatingActionButton? = null

    private val retrofitInit = RetrofitInit().init()
    private val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
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
        val call = jsonPlaceHolderApi.getAllGallList()

        call.enqueue(object : Callback<GalleryResponse> {
            override fun onResponse(call: Call<GalleryResponse>, response: Response<GalleryResponse>) {
                if (response.isSuccessful){
                    val data = response.body()!!
                    val galleryAdapter = GalleryAdapter(this@GalleryActivity, data.content)
                    val gallListView = findViewById<ListView>(R.id.gall_list)
                    gallListView.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            val it = Intent(
                                this@GalleryActivity.applicationContext,
                                GalleryDetailActivity::class.java
                            )
                            it.putExtra("gall_id", data.content[position].id)
                            startActivity(it)
                        }
                    gallListView.adapter = galleryAdapter
                }else{
                    Log.e(TAG, "onResponse: ${response.code()}")
                    Log.e(TAG, "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<GalleryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}