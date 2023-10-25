package com.android16K.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.android16K.R
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
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
        val call = jsonPlaceHolderApi.loginProcess(testData)

        call.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i(TAG, "onResponse: ${response.isSuccessful}")
                if(response.isSuccessful){
                    val data = response.body()
                    Log.i(TAG, "onResponse: $data")
                }else{
                    Log.e(TAG, "onResponse: ${response.code()}")
                    Log.e(TAG, "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e(TAG, "onFailure: $call")
                Log.e(TAG, "onFailure: ${t.message}", )
            }

        })

    }
}