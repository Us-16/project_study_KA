package com.android16K.activity

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
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android16K.databinding.ActivityTestBinding
import com.android16K.view_model.GallDetailViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class TestActivity: AppCompatActivity() {
    private val gallDetailViewModel = GallDetailViewModel()
    private lateinit var testBinding: ActivityTestBinding

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val imageUri = result.data?.data ?: return@registerForActivityResult
            Log.i(TAG, "imageUri: $imageUri")

            val file = File(absolutePath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("profile", file.name, requestFile)

            lifecycleScope.launch {
                gallDetailViewModel.uploadImage(body, 1009L)
            }

            testBinding.testUploadImage.setImageURI(imageUri)
        }
    }

    private fun absolutePath(path: Uri?, context: Context): String {
        var proj:Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        Log.i(TAG, "selectGallery: $writePermission / $readPermission")

        //writePermission == PackageManager.PERMISSION_DENIED ||
        if(readPermission == PackageManager.PERMISSION_DENIED){
            Log.i(TAG, "selectGallery: conditional")
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            Log.i(TAG, "selectGallery: else")
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

            imageResult.launch(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        testBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(testBinding.root)

        testBinding.testUploadImageButton.setOnClickListener { selectGallery() }
    }
}
