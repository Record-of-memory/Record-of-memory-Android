package com.recordOfMemory.src.main.signUp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentLoginBinding
import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Service
import com.recordOfMemory.src.main.signUp.retrofit.SignUpFragmentInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService
import com.recordOfMemory.src.main.signUp.models.*
import java.util.regex.Pattern

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login), SignUpFragmentInterface {
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
        //비밀번호 찾기 버튼 안보이게 해두기(임시)
        viewBinding.searchPswdBtn.isGone = true

        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
//            signUpActivity!!.openFragmentSignUp(0)
            requireActivity().supportFragmentManager.popBackStack()
        }
        //로그인 버튼 누르면
        viewBinding.loginBtn.setOnClickListener {
            //이메일이 형식에 맞고, 이메일과 비밀번호 모두 빈칸이 아닐 때
            if (checkEmail() and viewBinding.editPswd.text.toString().isNotEmpty()) {
                val postSignInRequest = PostSignInRequest(
                    email = viewBinding.editEmail.text.toString(),
                    password = viewBinding.editPswd.text.toString()
                )
                showLoadingDialog(requireContext())
                SignUpService(this).tryPostSignIn(postSignInRequest)
            } else {
                //빈칸이 있으면 토스트 메시지 띄우기
                Toast.makeText(activity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        viewBinding.editEmail.setOnKeyListener { v, keyCode, event ->
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
                viewBinding.editPswd.requestFocus()
                viewBinding.editPswd.isCursorVisible = true
                viewBinding.editPswd.text.clear()
            }

            true
        }
        viewBinding.editPswd.setOnKeyListener { v, keyCode, event ->
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
                viewBinding.loginBtn.performClick()
            }

            true
        }
//        //비밀번호 찾기 버튼 누르면
//        viewBinding.searchPswdBtn.setOnClickListener {
//            signUpActivity!!.openFragmentSignUp(6)
//        }
    }

    //이메일이 형식에 맞는지 확인하는 메소드
    fun checkEmail():Boolean{
        //이메일 검사 정규식
        val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        var email = viewBinding.editEmail.text.toString().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞는지 확인
        if (p) {
            //이메일 형태가 정상일 경우
            return true
        } else {
            //정상이 아니면 토스트 메시지 띄우기
            Toast.makeText(activity, "이메일 형식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    //입력된 비밀번호가 틀렸음을 보여주는 메소드
    private fun wrongPasswordDialogFunction(){
        val logoutDialog = Dialog(requireContext())
        logoutDialog.setContentView(R.layout.dialog_custom2)
        logoutDialog.findViewById<TextView>(R.id.dialog2_title).text = "잘못된 비밀번호입니다."
        logoutDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutDialog.findViewById<TextView>(R.id.dialog2_btn_ok).setOnClickListener {
            // dialog 내림
            logoutDialog.dismiss()
        }
        logoutDialog.show()
    }

    override fun onPostSignUpSuccess(response: BaseResponse) {
    }

    override fun onPostSignUpFailure(message: String) {
    }

    override fun onPostSignInSuccess(response: TokenResponse) {
        dismissLoadingDialog()

        Log.d("로그인 성공","성공")

        println("response.accessToken : ${response.information.accessToken}")
        
        // 토큰 저장
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        signUpActivity!!.openNextActivity()
    }

    override fun onPostSignInWrong(message: String) {
        dismissLoadingDialog()
        Log.d("로그인 실패","비밀번호 다름")
        wrongPasswordDialogFunction()
    }

    override fun onPostSignInFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(activity, "오류 : $message", Toast.LENGTH_LONG).show()
        Log.d("로그인 오류","$message")
    }

    override fun onPostChangePasswordSuccess(response: BaseResponse) {}

    override fun onPostChangePasswordFailure(message: String) {}

    override fun onGetUserEmailCheckExist(response: UserEmailCheckResponse) {}

    override fun onGetUserEmailCheckNotExist(message: String) {}

    override fun onGetUserEmailCheckFailure(message: String) {}

    override fun onGetUserEmailCheckNoTokenExist(response: UserEmailCheckNoTokenResponse) {}

    override fun onGetUserEmailCheckNoTokenNotExist(message: String) {}

    override fun onGetUserEmailCheckNoTokenFailure(message: String) {}
}