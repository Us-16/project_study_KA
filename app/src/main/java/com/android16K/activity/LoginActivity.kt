package com.android16K.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View.OnClickListener
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.android16K.databinding.ActivityLoginBinding
import com.android16K.dataset.AuthenticationInfo
import com.android16K.view_model.LoginViewModel
import kotlinx.coroutines.launch
import okhttp3.Headers
import okhttp3.ResponseBody

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding:ActivityLoginBinding
    private val loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
    }

    override fun onStart(){
        super.onStart()
        loginBinding.loginLoginButton.setOnClickListener(loginListener)
        loginBinding.loginTestButton.setOnClickListener(goToTestListener)
        loginBinding.loginAccountHy.setOnClickListener(toAccountAct)
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
        loginFlow(loginBinding.loginUsername.text, loginBinding.loginPassword.text)
    }

    private fun loginFlow(username: Editable?, password: Editable?){
        lifecycleScope.launch {
            val result = loginViewModel.loginAction(username.toString(), password.toString())
            when(result.isSuccessful){
                true -> loginProcess(result.headers(), result.body())
                false -> getServerCode(result.code(), result.errorBody())
            }
        }
    }

    private fun getServerCode(code: Int, errorBody: ResponseBody?) {
        //TODO("이후에 토스트 말고 화면 내에 빨간색으로 표시해주세요")
        Toast.makeText(this.applicationContext, "code: $code\nmsg:$errorBody", Toast.LENGTH_SHORT).show()
    }

    private fun loginProcess(header: Headers, data: HashMap<String, Any>?) {
        when(data?.containsKey("code")){
            true -> Toast.makeText(this.applicationContext, "Login Failure ", Toast.LENGTH_LONG).show()
            else -> loginSuccess(header, data)
        }
    }

    private fun loginSuccess(header: Headers, data: HashMap<String, Any>?){
        val cookieHeader = header["Set-Cookie"]?.split(";")?.get(0)
        val authenticationInfo = AuthenticationInfo.getInstance()
        authenticationInfo.username = data!!["username"].toString()
        authenticationInfo.authorities = data["authorities"] as List<String>
        authenticationInfo.jSessionId = cookieHeader?.split("=")?.get(1)

        // Go to Gallery List
        val intent = Intent(this.applicationContext, GalleryActivity::class.java)
        startActivity(intent)
        finish()
    }
}