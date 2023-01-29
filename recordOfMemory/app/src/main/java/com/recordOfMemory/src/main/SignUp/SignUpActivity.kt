package com.recordOfMemory.src.main.signUp

import android.content.Intent
import android.os.Bundle
import com.recordOfMemory.config.BaseActivity
import com.recordOfMemory.databinding.ActivitySignUpBinding
import com.recordOfMemory.src.main.MainActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {

    private var startFragment = StartFragment()
    private var loginFragment = LoginFragment()
    private var acceptTermsFragment = AcceptTermsFragment()
    private var signUpEmailFragment = SignUpEmailFragment()
    private var signUpPswdFragment = SignUpPswdFragment()
    private var signUpNicknameFragment = SignUpNicknameFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //fragment 레이아웃 보이게 하기, 시작은 startFragment
        supportFragmentManager.beginTransaction().replace(binding.signUpFrm.id, startFragment).commitAllowingStateLoss()


    }

    // activity 바꾸는 메소드
    fun openNextActivity() {
        intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    // fragment 바꾸는 메소드
    fun openFragmentSignUp(int: Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            0 -> transaction.replace(binding.signUpFrm.id, startFragment)
            1 -> transaction.replace(binding.signUpFrm.id, loginFragment)
            2 -> transaction.replace(binding.signUpFrm.id, acceptTermsFragment)
            3 -> transaction.replace(binding.signUpFrm.id, signUpEmailFragment)
            4 -> transaction.replace(binding.signUpFrm.id, signUpPswdFragment)
            5 -> transaction.replace(binding.signUpFrm.id, signUpNicknameFragment)
        }
        transaction.commit()
    }
}