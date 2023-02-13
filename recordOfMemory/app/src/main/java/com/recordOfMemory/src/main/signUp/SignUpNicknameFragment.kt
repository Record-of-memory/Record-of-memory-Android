package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentLoginBinding
import com.recordOfMemory.databinding.FragmentSignUpNicknameBinding
import com.recordOfMemory.src.main.signUp.models.PostSignUpRequest
import com.recordOfMemory.src.main.signUp.models.TokenResponse
import com.recordOfMemory.src.main.signUp.models.UserEmailCheckNoTokenResponse
import com.recordOfMemory.src.main.signUp.models.UserEmailCheckResponse
import com.recordOfMemory.src.main.signUp.retrofit.SignUpFragmentInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService

class SignUpNicknameFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login),
    SignUpFragmentInterface {
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
                val email = arguments?.getString("email")
                Log.d("이메일","$email")
                val password = arguments?.getString("password")
                Log.d("비밀번호","$password")
                val nickname = viewBinding.editName.text.toString()

                val postSignUpRequest = PostSignUpRequest(email.toString(), password.toString(), nickname)
                SignUpService(this).tryPostSignUp(postSignUpRequest)
            } else {
                Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        viewBinding.editName.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                val editable = (v as EditText).text
                val start = v.selectionStart
                val end = v.selectionEnd
                if (start == end) {
                    if (start > 0) {
                        editable.delete(start - 1, start)
                    }
                } else {
                    editable.delete(start, end)
                }
            } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // 엔터 눌렀을때 행동
                //editName에 닉네임이 입력되어 있다면
                if (viewBinding.editName.text.toString().isNotEmpty()) {
                    val email = arguments?.getString("email")
                    Log.d("이메일","$email")
                    val password = arguments?.getString("password")
                    Log.d("비밀번호","$password")
                    val nickname = viewBinding.editName.text.toString()

                    val postSignUpRequest = PostSignUpRequest(email.toString(), password.toString(), nickname)
                    SignUpService(this).tryPostSignUp(postSignUpRequest)
                } else {
                    Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun onPostSignUpSuccess(response: BaseResponse) {
        //화면 넘어가기
        signUpActivity!!.openNextActivity()
    }

    override fun onPostSignUpFailure(message: String) {
        Log.d("회원가입 오류","$message")
        Toast.makeText(activity, "오류 : $message", Toast.LENGTH_LONG).show()
    }

    override fun onPostSignInSuccess(response: TokenResponse) {}

    override fun onPostSignInWrong(message: String) {}

    override fun onPostSignInFailure(message: String) {}

    override fun onPostChangePasswordSuccess(response: BaseResponse) {}

    override fun onPostChangePasswordFailure(message: String) {}

    override fun onGetUserEmailCheckExist(response: UserEmailCheckResponse) {}

    override fun onGetUserEmailCheckNotExist(message: String) {}

    override fun onGetUserEmailCheckFailure(message: String) {}

    override fun onGetUserEmailCheckNoTokenExist(response: UserEmailCheckNoTokenResponse) {}

    override fun onGetUserEmailCheckNoTokenNotExist(message: String) {}

    override fun onGetUserEmailCheckNoTokenFailure(message: String) {}
}