package com.android16K.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View.OnClickListener
import android.widget.*
import com.android16K.R
import com.android16K.dataset.*
import com.android16K.dataset.gall.Gallery
import com.android16K.dataset.gall.RequestGallery
import com.android16K.retrofit.*
import retrofit2.*

class GalleryFormActivity : AppCompatActivity() {
    private var title: EditText? = null
    private var content: EditText? = null
    private var saveButton: Button? = null
    //private var imageButton: Button? = null
    //private var image: ImageView? = null

    //private var imageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_form)

        saveButton = findViewById(R.id.gall_form_saveButton)
        title = findViewById(R.id.gall_form_title)
        content = findViewById(R.id.gall_form_content)

        //imageButton = findViewById(R.id.gall_form_imageButton)
        //image = findViewById(R.id.gall_form_image)
    }

    override fun onStart() {
        super.onStart()
        saveButton!!.setOnClickListener(saveGallery)
        //imageButton!!.setOnClickListener(imageSave)
    }

    /*private val imageSave:OnClickListener = OnClickListener {
        val image = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(image, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100){
            val imageUri = data?.data
            imageURI = imageUri
            image!!.setImageURI(imageUri)
        }
    }*/

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
        val jsonPlaceHolderApi = RetrofitInstance.api
        val authenticationInfo = AuthenticationInfo.getInstance()

        val call = jsonPlaceHolderApi.createGall(RequestGallery(title.toString(), content.toString(), "TEST", authenticationInfo.username))
        
        call.enqueue(object : Callback<Gallery> {
            override fun onResponse(call: Call<Gallery>, response: Response<Gallery>) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: Success") //14:26 확인했습니다.
                    //go To Detail
                    val it = Intent(this@GalleryFormActivity.applicationContext, GalleryDetailActivity::class.java)
                    it.putExtra("gall_id", response.body()!!.id)
                    startActivity(it)
                    finish()
                }else {
                    Log.e(TAG, "onResponse: ${response.code()}, ${response.errorBody()}",)
                }
            }

            override fun onFailure(call: Call<Gallery>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    /*private fun sendImage(){
        val retrofitInit = RetrofitInit().init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)

        val file = imageURI!!.path?.let { File(it) }
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val imagePart = MultipartBody.Part.createFormData("upload_file", file!!.name + ".jpeg", requestFile)

        val call = jsonPlaceHolderApi.createImage(imagePart)

        call.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.d(TAG, "onResponse: $response")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }*/
}