package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.android16K.R
import com.android16K.adapter.GalleryAdapter
import com.android16K.dataset.Account
import com.android16K.dataset.Gallery
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_actvity)

        getAndPutData()
    }
    
    private fun getAndPutData(){
        val retrofitInit = RetrofitInit().init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAllGallList()
        
        
        call.enqueue(object : Callback<List<Gallery>> {
            override fun onResponse(call: Call<List<Gallery>>, response: Response<List<Gallery>>) {
                if (response.isSuccessful){
                    val data = response.body()
                    Log.i(TAG, "onResponse: $data")
                    val galleryAdapter = GalleryAdapter(this@GalleryActivity, data)
                    val gallListView = findViewById<ListView>(R.id.gall_list)
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