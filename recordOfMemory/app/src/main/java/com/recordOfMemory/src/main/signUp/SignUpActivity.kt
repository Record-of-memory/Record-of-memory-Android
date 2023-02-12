package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
        finishAffinity()
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
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}