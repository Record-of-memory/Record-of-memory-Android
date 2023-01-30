package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recordOfMemory.databinding.FragmentSignUpPswdBinding

class SignUpPswdFragment : Fragment() {
    private lateinit var viewBinding: FragmentSignUpPswdBinding

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
        viewBinding = FragmentSignUpPswdBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(3)
        }
        //다음 버튼 누르면
        viewBinding.nextBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(5)
        }
    }
}