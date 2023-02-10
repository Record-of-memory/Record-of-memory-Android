package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.recordOfMemory.databinding.FragmentSignUpNicknameBinding

class SignUpNicknameFragment : Fragment() {
    private lateinit var viewBinding: FragmentSignUpNicknameBinding

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
        viewBinding = FragmentSignUpNicknameBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(4)
        }
        //다음 버튼 누르면
        viewBinding.nextBtn.setOnClickListener {
            //editName에 닉네임이 입력되어 있다면
            if (viewBinding.editName.text.toString().isNotEmpty()) {
                signUpActivity!!.openNextActivity()
            } else {
                Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}