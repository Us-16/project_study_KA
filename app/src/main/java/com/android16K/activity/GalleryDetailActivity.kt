package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.marginBottom
import com.android16K.R
import com.android16K.dataset.Gallery
import com.android16K.dataset.GalleryImage
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryDetailActivity : AppCompatActivity() {
    private val retrofitInit = RetrofitInit().init()
    private val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)

    private var title: TextView? = null
    private var content: TextView? = null
    private var author: TextView? = null
    private var date: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_detail)

        val gallId = intent.getLongExtra("gall_id", -1)

        loadElement()

        getGalleryData(gallId)
        getGalleryImage(gallId)
        getGalleryAnswer(gallId)
    }

    private fun loadElement() {
        title = findViewById(R.id.gall_detail_title)
        content = findViewById<TextView>(R.id.gall_detail_content)
        date = findViewById<TextView>(R.id.gall_detail_date)
        author = findViewById<TextView>(R.id.gall_detail_author)
    }

    private fun getGalleryImage(gallId: Long) {
        val call = jsonPlaceHolderApi.getGallImageList(gallId)

        call.enqueue(object: Callback<List<GalleryImage>>{
            override fun onResponse(
                call: Call<List<GalleryImage>>,
                response: Response<List<GalleryImage>>
            ) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.body()}")
                    val data = response.body()
                    val layout = findViewById<LinearLayoutCompat>(R.id.gall_detail_imageContainer)
                    imageLoad(layout, data!!)
                }
            }

            override fun onFailure(call: Call<List<GalleryImage>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", null)
            }
        })
    }

    private fun imageLoad(layout: LinearLayoutCompat, imageList:List<GalleryImage>){
        for(image in imageList){
            val imageView = ImageView(this@GalleryDetailActivity.applicationContext)
            val imageParam = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, //width
                1000
                //LinearLayoutCompat.LayoutParams.WRAP_CONTENT, //height
            )
            imageParam.marginStart = 50
            imageParam.topMargin = 50

            imageView.layoutParams = imageParam

            Glide.with(this@GalleryDetailActivity.applicationContext)
                .load("http://49.173.81.98:8080"+image.path)
                .into(imageView)

            layout.addView(imageView)
        }
    }


    private fun getGalleryAnswer(gallId: Long) {

    }

    private fun getGalleryData(gallId:Long){
        val call = jsonPlaceHolderApi.getGallItem(gallId)

        call.enqueue(object:Callback<Gallery>{
            override fun onResponse(call: Call<Gallery>, response: Response<Gallery>) {
                if(response.isSuccessful){
                    val data = response.body()
                    title!!.text = data!!.title
                    content!!.text = data.content
                    author!!.text=  data.account.username
                    date!!.text = data.modifiedDate?.let { dateStyle(it) } ?: dateStyle(data.createDate)

                    Log.d(TAG, "GalleryDetail: $data")
                }
            }

            override fun onFailure(call: Call<Gallery>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", null)
            }

        })
    }

    private fun dateStyle(date: String): String {
        val dateSplit = date.split("T")
        return dateSplit[0] + " " + dateSplit[1].split(".")[0]
    }
}