package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recordOfMemory.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
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
            signUpActivity!!.openNextActivity()
        }
        //비밀번호 찾기 버튼 누르면
        viewBinding.searchPswdBtn.setOnClickListener {
            """
                디자인 미완성
            """
        }
    }
}