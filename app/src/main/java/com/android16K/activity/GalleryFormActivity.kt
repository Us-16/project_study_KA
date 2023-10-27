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
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.system.exitProcess

//TODO("글을 작성하고 저장합니다. 최종적으로 본인이 작성한 액티비티(Detail)로 이동해야합니다.")
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
        Log.d(TAG, "sendData: ${authenticationInfo.username}, ${authenticationInfo.authorities}, ${authenticationInfo.jSessionId}")
        //val call = jsonPlaceHolderApi.createGall(title, content, classify, account)

        //call 25685CD4CA317A24C4406B20C1C02C4F
    }
}