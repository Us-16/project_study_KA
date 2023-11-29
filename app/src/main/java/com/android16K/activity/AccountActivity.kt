package com.android16K.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.android16K.R
import com.android16K.databinding.ActivityAccountBinding
import com.android16K.dataset.Account
import com.android16K.view_model.AccountViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AccountActivity : AppCompatActivity() {
    private lateinit var accountBinding: ActivityAccountBinding
    private val accountViewModel = AccountViewModel()

    /*
    * 중복 및 확인 버튼 규칙: 문제가 없는 경우에 true, 문제가 있는 경우 false
    * 예) 아이디 중복 O -> 문제 발생 -> false / 아이디 중복 X -> 문제 없음 -> true
    */
    private var isUsernameDup = false
    private var isPasswordConfirm = false
    private var isTelCert = false

    private var answerCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountBinding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(accountBinding.root)
    }
    override fun onStart() {
        super.onStart()
        accountBinding.accountUsernameDupButton.setOnClickListener(usernameDupListener)
        accountBinding.accountPasswordConfirmButton.setOnClickListener(confirmPassword)
        accountBinding.accountTelCertButton.setOnClickListener(sendCode)
        accountBinding.accountTelCheckButton.setOnClickListener(checkCode)
        accountBinding.accountCreateButton.setOnClickListener(createAccount)
    }
    private fun afterSave(result: Boolean){
        if(result){
            Toast.makeText(this@AccountActivity.applicationContext, "회원가입이 완료되었습니다.\n로그인 페이지로 이동합니다.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@AccountActivity.applicationContext, LoginActivity::class.java))
            finish()
        }else{
            Toast.makeText(this@AccountActivity.applicationContext, "데이터 저장 간 오류가 발생했습니다.\n다시 시도해주세요", Toast.LENGTH_LONG).show()
        }
    }
    private fun saveData(account:Account){
        lifecycleScope.launch {
            val response = accountViewModel.createAccount(account)
            when(response.isSuccessful){
                true -> afterSave(response.body()!!)
                false -> Toast.makeText(this@AccountActivity.applicationContext, "데이터 저장 간 오류가 발생했습니다.\n다시 시도해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val createAccount:OnClickListener = OnClickListener {
        if(!isTelCert || !isUsernameDup || !isPasswordConfirm || accountBinding.accountName.text.toString()
                .isEmpty()){
            Toast.makeText(this@AccountActivity.applicationContext, "내용을 모두 작성해주세요", Toast.LENGTH_LONG).show()
        }else{
            saveData(Account(
                username = accountBinding.accountUsername.text.toString(),
                password = accountBinding.accountPassword.text.toString(),
                name = accountBinding.accountName.text.toString(),
                tel = accountBinding.accountTel.text.toString(),
                role = "USER",
                createDate = LocalDateTime.now().toString()
            ))
        }
    }

    private val confirmPassword:OnClickListener = OnClickListener {
        isPasswordConfirm = (accountBinding.accountPassword.text.toString() == accountBinding.accountPasswordConfirm.text.toString() && accountBinding.accountPassword.text.isNotEmpty())
        val textColor = if(isPasswordConfirm) Color.BLUE else Color.RED
        val resultText = if(isPasswordConfirm) "비밀번호 확인이 완료되었습니다." else "비밀번호를 다시 작성해주세요"

        accountBinding.accountPasswordResult.text = resultText
        accountBinding.accountPasswordResult.setTextColor(textColor)
    }

    /*TODO(usernameDup, passwordCheck, telCheck 모두 정규식 확인 필요 -> 리팩토링 과정에서 해결바람)*/
    private val usernameDupListener:OnClickListener = OnClickListener {
        lifecycleScope.launch {
            isUsernameDup = accountViewModel.isUsernameDup(accountBinding.accountUsername.text.toString())!!
            val textColor = if(isUsernameDup) Color.BLUE else Color.RED
            val resultText = if (isUsernameDup) "사용가능한 아이디입니다." else "사용할 수 없는 아이디입니다."

            accountBinding.accountUsernameResult.text = resultText
            accountBinding.accountUsernameResult.setTextColor(textColor)
        }
    }

    private val sendCode:OnClickListener = OnClickListener {
        lifecycleScope.launch {
            val result = accountViewModel.isTelDup(accountBinding.accountTel.text.toString())
            if(result!!){ getAnswerCode() }else{
                accountBinding.accountTelResult.text = "이미 등록된 전화번호입니다."
                accountBinding.accountTelResult.setTextColor(Color.RED)
            }
        }
    }

    private fun getAnswerCode(){
        lifecycleScope.launch{
            answerCode = accountViewModel.getAnswerCode(accountBinding.accountTel.text.toString()).toString()
            accountBinding.accountTelResult.text = "인증번호를 전송했습니다."
            accountBinding.accountTelResult.setTextColor(Color.BLUE)
        }
    }

    private val checkCode:OnClickListener = OnClickListener {
        isTelCert = (accountBinding.accountTelCert.text.toString() == answerCode)
        val resultText = if(isTelCert) "확인이 완료되었습니다." else "인증번호를 다시 확인해주세요"
        val textColor = if(isTelCert) Color.BLUE else Color.RED

        accountBinding.accountTelResult.text = resultText
        accountBinding.accountTelResult.setTextColor(textColor)
    }
}