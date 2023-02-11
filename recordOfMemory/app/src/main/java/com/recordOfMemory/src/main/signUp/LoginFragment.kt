package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentLoginBinding
import com.recordOfMemory.src.main.signUp.retrofit.SignUpFragmentInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService
import com.recordOfMemory.src.main.signUp.models.*

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login), SignUpFragmentInterface {
    var signUpActivity: SignUpActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = context as SignUpActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //뒤로가기 버튼 누르면
        binding.backBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(0)
        }
        //로그인 버튼 누르면
        binding.loginBtn.setOnClickListener {
            showLoadingDialog(requireContext())
            val postSignInRequest = PostSignInRequest(email = binding.editEmail.text.toString(), password = binding.editPswd.text.toString())
            SignUpService(this).tryPostSignIn(postSignInRequest)
        }
        //비밀번호 찾기 버튼 누르면
        //binding.searchPswdBtn.setOnClickListener {
     //       """
   //             디자인 미완성
 //           """
        //}
    }

    override fun onPostSignUpSuccess(response: BaseResponse) {
    }

    override fun onPostSignUpFailure(message: String) {
    }

    override fun onPostSignInSuccess(response: TokenResponse) {
        Log.d("로그인 성공","성공")
        dismissLoadingDialog()

        println("response.accessToken : ${response.information.accessToken}")
        
        // 토큰 저장
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()
        signUpActivity!!.openNextActivity()
    }

    override fun onPostSignInFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(activity, "오류 : $message", Toast.LENGTH_LONG).show()
        Log.d("로그인 오류","$message")
    }

//    override fun onPostRefreshSuccess(response: TokenResponse) {}
//
//    override fun onPostRefreshFailure(message: String) {}


    override fun onGetUserEmailCheckSuccess(response: UserEmailCheckResponse) {}

    override fun onGetUserEmailCheckFailure(message: String) {}
}