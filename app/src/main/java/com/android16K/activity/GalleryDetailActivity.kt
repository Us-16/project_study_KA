package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.marginBottom
import com.android16K.R
import com.android16K.dataset.Answer
import com.android16K.dataset.AuthenticationInfo
import com.android16K.dataset.Gallery
import com.android16K.dataset.GalleryImage
import com.android16K.dataset.RequestAnswer
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class GalleryDetailActivity : AppCompatActivity() {
    private val retrofitInit = RetrofitInit().init()
    private val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)

    private var title: TextView? = null
    private var content: TextView? = null
    private var author: TextView? = null
    private var date: TextView? = null
    private var layout: LinearLayoutCompat? = null
    private var answerSaveButton: Button? = null

    private var gallId:Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_detail)

        gallId = intent.getLongExtra("gall_id", -1)

        loadElement()

        getGalleryData(gallId!!)
        getGalleryImage(gallId!!)
        getGalleryAnswer(gallId!!)
    }

    override fun onStart() {
        super.onStart()
        answerSaveButton!!.setOnClickListener(saveAnswer)
    }

    private fun loadElement() {
        title = findViewById(R.id.gall_detail_title)
        content = findViewById(R.id.gall_detail_content)
        date = findViewById(R.id.gall_detail_date)
        author = findViewById(R.id.gall_detail_author)

        layout = findViewById(R.id.gall_detail_imageContainer)

        answerSaveButton = findViewById(R.id.gall_detail_saveAnswerButton)
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

                    layout?.let { imageLoad(it, data!!) }
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
            )
            imageParam.marginStart = 50
            imageParam.topMargin = 150

            imageView.layoutParams = imageParam

            Glide.with(this@GalleryDetailActivity.applicationContext)
                .load("http://49.173.81.98:8080"+image.path)
                .into(imageView)

            layout.addView(imageView)
        }
    }

    private fun answerLoad(layout: LinearLayoutCompat, answerList:List<Answer>){
        for(answer in answerList){
            val contentText = TextView(this@GalleryDetailActivity.applicationContext)
            val textViewParam = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            textViewParam.topMargin = 50
            textViewParam.marginStart = 50

            contentText.layoutParams = textViewParam

            contentText.text = answer.account.username+ "\n" + answer.content + "\n" + answer.createdDate.split("T")[0] + " " +answer.createdDate.split("T")[1].split(".")[0]

            layout.addView(contentText)
        }
    }

    private val saveAnswer: OnClickListener = OnClickListener {
        val answerInput = findViewById<EditText>(R.id.gall_detail_answerInput)
        val authenticationInfo = AuthenticationInfo.getInstance()
        val call = jsonPlaceHolderApi.createAnswer(
            RequestAnswer(content = answerInput.text.toString(),
                gallId = gallId!!.toLong(),
                username = authenticationInfo.username!!))
        call.enqueue(object:Callback<Long>{
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                if(response.isSuccessful){
                    val data = response.body() as Long
                    Log.d(TAG, "onResponse: ${data}") //-1을 받으면 실패
                    if(data > 0L){
                        getGalleryAnswer(gallId!!)
                        answerInput.text.clear()
                    }else{
                        Toast.makeText(this@GalleryDetailActivity.applicationContext, "답변 등록에 실패했습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", null)
            }
        })
    }

    private fun getGalleryAnswer(gallId: Long) {
        val call = jsonPlaceHolderApi.getAllAnswer(gallId)

        call.enqueue(object:Callback<List<Answer>>{
            override fun onResponse(call: Call<List<Answer>>, response: Response<List<Answer>>) {
                if(response.isSuccessful){
                    val data = response.body()!!
                    val answerContainer = findViewById<LinearLayoutCompat>(R.id.gall_detail_answerContainer)
                    answerLoad(answerContainer, data)
                }
            }

            override fun onFailure(call: Call<List<Answer>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", null)
            }
        })
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