package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.recordOfMemory.databinding.FragmentLoginBinding
import com.recordOfMemory.src.main.signUp.models.*

class LoginFragment : Fragment(), SignUpFragmentInterface {
    private lateinit var viewBinding: FragmentLoginBinding

    var signUpActivity: SignUpActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = context as SignUpActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(0)
        }
        //로그인 버튼 누르면
        viewBinding.loginBtn.setOnClickListener {
            val postSignInRequest = PostSignInRequest(email = viewBinding.editEmail.text.toString(), password = viewBinding.editPswd.text.toString())
            SignUpService(this).tryPostSignIn(postSignInRequest)
        }
        //비밀번호 찾기 버튼 누르면
        //viewBinding.searchPswdBtn.setOnClickListener {
     //       """
   //             디자인 미완성
 //           """
        //}
    }

    override fun onPostSignUpSuccess(response: SignUpResponse) {
    }

    override fun onPostSignUpFailure(message: String) {
    }

    override fun onPostSignInSuccess(response: SignInResponse) {
        Log.d("로그인 성공","성공")
        signUpActivity!!.openNextActivity()
    }

    override fun onPostSignInFailure(message: String) {
        Toast.makeText(activity, "오류 : $message", Toast.LENGTH_LONG).show()
        Log.d("로그인 오류","$message")
    }

    override fun onPostRefreshSuccess(response: RefreshResponse) {}

    override fun onPostRefreshFailure(message: String) {}

    override fun onPostChangePasswordSuccess(response: ChangePasswordResponse) {}

    override fun onPostChangePasswordFailure(message: String) {}

    override fun onGetUserEmailCheckSuccess(response: UserEmailCheckResponse) {}

    override fun onGetUserEmailCheckFailure(message: String) {}
}