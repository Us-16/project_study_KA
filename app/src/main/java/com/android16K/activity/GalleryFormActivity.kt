package com.android16K.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android16K.databinding.ActivityGalleryFormBinding
import com.android16K.dataset.*
import com.android16K.dataset.gall.*
import com.android16K.view_model.GallDetailViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GalleryFormActivity : AppCompatActivity() {
    private lateinit var formBinding: ActivityGalleryFormBinding
    private val gallViewModel = GallDetailViewModel()
    private lateinit var body: MultipartBody.Part

    private lateinit var gallInstance: GalleryDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        formBinding = ActivityGalleryFormBinding.inflate(layoutInflater)
        setContentView(formBinding.root)
    }

    override fun onStart() {
        super.onStart()
        formBinding.gallFormSaveButton.setOnClickListener(saveGallery)
        formBinding.gallFormImageButton.setOnClickListener(selectImage)
        formBinding.gallFormUpdateButton.setOnClickListener(sendUpdate)

        val gallId = intent.getLongExtra("isUpdate", -1)
        if(gallId != -1L){
            fillData()
            //수정버튼 보이도록
            formBinding.gallFormUpdateButton.visibility = View.VISIBLE
            formBinding.gallFormSaveButton.visibility = View.INVISIBLE
        }else{
            //저장버튼 보이도록
            formBinding.gallFormUpdateButton.visibility = View.INVISIBLE
            formBinding.gallFormSaveButton.visibility = View.VISIBLE
        }

    }

    private fun fillData() {
        gallInstance = GalleryDTO.getInstance()
        formBinding.gallFormTitle.setText(gallInstance!!.title)
        formBinding.gallFormContent.setText(gallInstance!!.content)
    }

    private fun checkEmpty(title: Editable, content: Editable): Boolean {
        return title.isNotEmpty() && content.isNotEmpty()
    }

    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutePath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            body = MultipartBody.Part.createFormData("profile", file.name, requestFile)

            formBinding.gallFormImageView.setImageURI(imageUri)
        }
    }

    @SuppressLint("Recycle")
    private fun absolutePath(path: Uri?, context: Context): String {
        val proj:Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c:Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }

    private val selectImage:OnClickListener = OnClickListener{
        val readPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

            imageResult.launch(intent)
        }
    }

    private val saveGallery:OnClickListener = OnClickListener{
        if(!checkEmpty(formBinding.gallFormTitle.text!!, formBinding.gallFormContent.text)) {
            //만약 비었다면
            Toast.makeText(this.applicationContext, "내용을 모두 작성해주세요", Toast.LENGTH_LONG).show()
        }else{
            sendData(formBinding.gallFormTitle.text!!, formBinding.gallFormContent.text)
        }
    }

    private fun sendData(title: Editable, content: Editable) {
        val authenticationInfo = AuthenticationInfo.getInstance()
        lifecycleScope.launch {
            val letterResult = gallViewModel.sendForm(RequestGallery(null, title.toString(), content.toString(), "TEST", authenticationInfo.username))
            letterResult.takeIf {it.isSuccessful}?.body()?.let { result ->
                val imageResult = gallViewModel.uploadImage(body, result.id)
                if(imageResult.isSuccessful){
                    val intent = Intent(this@GalleryFormActivity.applicationContext, GalleryDetailActivity::class.java)
                    intent.putExtra("gall_id", result.id)
                    startActivity(intent)
                    finish()
                }else{
                    Log.e(TAG, "onResponse: ${imageResult.code()}, ${imageResult.errorBody()}",)
                }
            }?: Log.e(TAG, "onResponse: ${letterResult.code()}, ${letterResult.errorBody()}", null)
        }
    }

    private val sendUpdate = OnClickListener{
        lifecycleScope.launch {
            val updateResult = gallViewModel.updateGall(RequestGallery(id = gallInstance.id, title = formBinding.gallFormTitle.text.toString(), content = formBinding.gallFormContent.text.toString(), username = AuthenticationInfo.getInstance().username))
            if (updateResult.isSuccessful){
                val intent = Intent(this@GalleryFormActivity.applicationContext, GalleryDetailActivity::class.java)
                intent.putExtra("gall_id", updateResult.body()!!.id)
                startActivity(intent)
                finish()
            }
        }
    }
}