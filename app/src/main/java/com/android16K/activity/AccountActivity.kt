package com.android16K.activity

import android.content.ContentValues.TAG
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android16K.R
import com.android16K.retrofit.JsonPlaceHolderApi
import com.android16K.retrofit.RetrofitInit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountActivity : AppCompatActivity() {
    private val retrofitInit = RetrofitInit().init()
    private val jsonPlaceHolderApi = retrofitInit.create(JsonPlaceHolderApi::class.java)

    /*
    * 중복 및 확인 버튼 규칙: 문제가 없는 경우에 true, 문제가 있는 경우 false
    * 예) 아이디 중복 O -> 문제 발생 -> false / 아이디 중복 X -> 문제 없음 -> true
    */
    private var isUsernameDup = false
    private var isPasswordConfirm = false
    private var isTelCert = false

    private var answerCode = ""

    /*Data*/
    private var username: EditText? = null
    private var password: EditText? = null
    private var passwordConfirm: EditText? = null //do not send
    private var name: EditText? = null
    private var birth: EditText? = null
    private var tel: EditText? = null
    private var telConfirm: EditText? = null //do not send

    /*Buttons*/
    private var usernameDupButton: Button? = null
    private var passwordConfirmButton: Button? = null
    private var telSendCodeButton: Button? = null
    private var telCheckButton: Button? = null

    /*Result*/
    private var usernameResult: TextView? = null
    private var passwordResult: TextView? = null
    private var telResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        elementInit()
    }

    private fun elementInit() {
        username = findViewById(R.id.account_username)
        password = findViewById(R.id.account_password)
        passwordConfirm = findViewById(R.id.account_passwordConfirm)
        name = findViewById(R.id.account_name)
        birth = findViewById(R.id.account_birth)
        tel = findViewById(R.id.account_tel)
        telConfirm = findViewById(R.id.account_telCert)

        usernameDupButton = findViewById(R.id.account_usernameDupButton)
        passwordConfirmButton = findViewById(R.id.account_passwordConfirmButton)
        telSendCodeButton = findViewById(R.id.account_telCertButton)
        telCheckButton = findViewById(R.id.account_telCheckButton)

        usernameResult = findViewById(R.id.account_usernameResult)
        passwordResult = findViewById(R.id.account_passwordResult)
        telResult = findViewById(R.id.account_telResult)
    }

    override fun onStart() {
        super.onStart()
        usernameDupButton!!.setOnClickListener(usernameDupListener)
        passwordConfirmButton!!.setOnClickListener(confirmPassword)
        telSendCodeButton!!.setOnClickListener(sendCode)
        telCheckButton!!.setOnClickListener(checkCode)
    }

    /*TODO(usernameDup, passwordCheck 양측 모두 정규식 확인 필요 -> 리팩토링 과정에서 해결바람)*/
    private val usernameDupListener:OnClickListener = OnClickListener {
        val call = jsonPlaceHolderApi.getUsernameDup(username!!.text.toString())
        call.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    isUsernameDup = response.body() as Boolean
                    when(isUsernameDup){
                        true -> {
                            usernameResult!!.text = "사용가능한 아이디입니다."
                            usernameResult!!.setTextColor(Color.BLUE)
                        }
                        else -> {
                            usernameResult!!.text = "사용할 수 없는 아이디입니다."
                            usernameResult!!.setTextColor(Color.RED)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", )
            }
        })
    }

    private val confirmPassword:OnClickListener = OnClickListener {
        isPasswordConfirm = (password!!.text.toString() == passwordConfirm!!.text.toString())
        Log.d(TAG, "PasswordResult: $isPasswordConfirm")
        Log.d(TAG, "PasswordResult-Input: ${password!!.text}")

        when(isPasswordConfirm){
            true -> {
                passwordResult!!.text = "비밀번호 확인이 완료되었습니다."
                passwordResult!!.setTextColor(Color.BLUE)
            }
            false -> {
                passwordResult!!.text = "비밀번호를 다시 작성해주세요"
                passwordResult!!.setTextColor(Color.RED)
            }
        }
    }
    
    private val sendCode:OnClickListener = OnClickListener {
        val call = jsonPlaceHolderApi.getTelDup(tel!!.text.toString())
        call.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    val telDup: Boolean = response.body() as Boolean // true -> 생성가능
                    when(telDup){
                        true -> getAnswerCode()
                        else -> {
                            telResult!!.text = "이미 등록된 전화번호입니다."
                            telResult!!.setTextColor(Color.RED)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                val call = jsonPlaceHolderApi.getAnswerCoder(tel!!.text.toString())
            }
        })
    }

    private fun getAnswerCode(){
        val call = jsonPlaceHolderApi.getAnswerCoder(tel!!.text.toString())
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.body()}")
                    answerCode = response.body() as String
                    Log.d(TAG, "answerCode: $answerCode")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "getAnswerCode: ${t.message}", null)
            }
        })
    }

    private val checkCode:OnClickListener = OnClickListener {
        isTelCert = (telConfirm!!.text.toString() == answerCode)
        when(isTelCert){
            true -> {
                telResult!!.text = "확인이 완료되었습니다."
                telResult!!.setTextColor(Color.BLUE)
            }
            false -> {
                telResult!!.text = "인증번호를 다시 확인해주세요"
                telResult!!.setTextColor(Color.RED)
            }
        }
        Log.d(TAG, "answerCode-Result: $isTelCert")
    }
}