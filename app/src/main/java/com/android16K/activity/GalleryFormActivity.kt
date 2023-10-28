package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android16K.R
import com.android16K.dataset.AuthenticationInfo
import com.android16K.dataset.RequestGallery
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

//TODO("최종적으로 본인이 작성한 액티비티(Detail)로 이동해야합니다.")
class GalleryFormActivity : AppCompatActivity() {
    private var title: EditText? = null
    private var content: EditText? = null
    private var saveButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_form)

        saveButton = findViewById(R.id.gall_form_saveButton)
        title = findViewById(R.id.gall_form_title)
        content = findViewById(R.id.gall_form_content)
    }

    override fun onStart() {
        super.onStart()
        saveButton!!.setOnClickListener(saveGallery)
    }

    private fun checkEmpty(title: Editable, content: Editable): Boolean {
        return title.isNotEmpty() && content.isNotEmpty()
    }

    private val saveGallery:OnClickListener = OnClickListener{
        if(!checkEmpty(title!!.text, content!!.text)) {
            //만약 비었다면
            Toast.makeText(this.applicationContext, "내용을 모두 작성해주세요", Toast.LENGTH_LONG).show()
        }else{
            sendData(title!!.text, content!!.text)
        }
    }

    private fun sendData(title: Editable, content: Editable) {
        val retrofitInit = RetrofitInit().init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val authenticationInfo = AuthenticationInfo.getInstance()
        
        val call = jsonPlaceHolderApi.createGall(RequestGallery(title.toString(), content.toString(), "TEST", authenticationInfo.username))
        
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: Success") //14:26 확인했습니다.
                }else{
                    Log.e(TAG, "onResponse: ${response.code()}, ${response.errorBody()}", )
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}