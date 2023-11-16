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
import com.android16K.dataset.AuthenticationInfo
import com.android16K.retrofit.*
import com.google.android.material.textfield.TextInputEditText
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.*

class LoginActivity : AppCompatActivity() {
    private var loginButton : Button? = null
    private var testButton: Button? = null

    private var toAccount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_loginButton)
        testButton = findViewById(R.id.login_testButton)

        toAccount = findViewById(R.id.login_accountHy)
    }

    override fun onStart(){
        super.onStart()
        loginButton!!.setOnClickListener(loginListener)
        testButton!!.setOnClickListener(goToTestListener)

        toAccount!!.setOnClickListener(toAccountAct)
    }

    private val toAccountAct: OnClickListener = OnClickListener {
        startActivity(Intent(this.applicationContext, AccountActivity::class.java))
    }

    private val goToTestListener: OnClickListener = OnClickListener {
        val intent = Intent(this.applicationContext, TestActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val loginListener: OnClickListener = OnClickListener {
        val usernameInput = findViewById<TextInputEditText>(R.id.login_username)
        val passwordInput = findViewById<TextInputEditText>(R.id.login_password)

        loginProcess(usernameInput.text, passwordInput.text)
    }

    private fun loginProcess(username: Editable?, password: Editable?){
        val retrofitInit = RetrofitInit().init()
        val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.loginProcess(username.toString(), password.toString())

        call.enqueue(object: Callback<HashMap<String, Any>>{
            override fun onResponse(
                call: Call<HashMap<String, Any>>,
                response: Response<HashMap<String, Any>>
            ) {
                when(response.isSuccessful){
                    true -> isResponseSuccess(response.headers(), response.body())
                    false -> getServerCode(response.code(), response.errorBody())
                }
            }

            override fun onFailure(call: Call<HashMap<String, Any>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun getServerCode(code: Int, errorBody: ResponseBody?) {
        //TODO("이후에 토스트 말고 화면 내에 빨간색으로 표시해주세요")
        Toast.makeText(this.applicationContext, "code: $code\nmsg:$errorBody", Toast.LENGTH_SHORT).show()
    }

    private fun isResponseSuccess(header: Headers, data: HashMap<String, Any>?) {
        var msg = ""
        if(data?.containsKey("code") == true){
            msg = "Login Failure"
            Toast.makeText(this.applicationContext, "$msg", Toast.LENGTH_LONG).show()
        }else{
            val cookieHeader = header["Set-Cookie"]?.split(";")?.get(0)
            val authenticationInfo = AuthenticationInfo.getInstance()
            authenticationInfo.username = "test"
            authenticationInfo.authorities = listOf("USER")
            authenticationInfo.jSessionId = cookieHeader?.split("=")?.get(1)

            // Go to Gallery List
            val intent = Intent(this.applicationContext, GalleryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}