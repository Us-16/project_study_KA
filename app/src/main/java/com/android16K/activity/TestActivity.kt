package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.android16K.R
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        sendData()
    }

    private fun sendData() {
        val retrofitInit = RetrofitInit().init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val testData = HashMap<String, String>()
        testData["username"] = "testUsername"
        
        val call = jsonPlaceHolderApi.loginProcess("test", "1234")
        
        call.enqueue(object: Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    Log.d(TAG, "this is successful site")
                    val data = response.body()
                    val cookieHeader = response.headers()["Set-Cookie"]?.split(";")?.get(0)
                    Log.d(TAG, "JSESSIONID: ${cookieHeader?.split("=")?.get(1)}")
                    Log.i(TAG, "onResponse: ${data}")
                }else{
                    Log.e(TAG, "onResponse: ${response.code()}", )
                    Log.e(TAG, "onResponse: ${response.errorBody()}", )
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", )
            }

        })

    }
}