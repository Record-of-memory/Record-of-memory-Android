package com.recordOfMemory.src.main.signUp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentLoginBinding
import com.recordOfMemory.databinding.FragmentSignUpEmailBinding
import com.recordOfMemory.src.main.signUp.models.TokenResponse
import com.recordOfMemory.src.main.signUp.models.UserEmailCheckNoTokenResponse
import com.recordOfMemory.src.main.signUp.models.UserEmailCheckResponse
import com.recordOfMemory.src.main.signUp.retrofit.SignUpFragmentInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService
import java.util.regex.Pattern

class SignUpEmailFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login),
    SignUpFragmentInterface {
    private lateinit var viewBinding: FragmentSignUpEmailBinding
    //이메일 검사 정규식
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

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
        viewBinding = FragmentSignUpEmailBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //시작할 땐 이메일 형식 에러 메시지 가려놓기
        viewBinding.tvEmailFormatError.visibility = View.INVISIBLE

        viewBinding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            //값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEmail()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
//            signUpActivity!!.openFragmentSignUp(2)
        }
        //다음 버튼 누르면
        viewBinding.nextBtn.setOnClickListener {
            if (checkEmail()) {
                val email = viewBinding.editEmail.text.toString()
                Log.d("입력받은 이메일",email)
                SignUpService(this).tryGetUserEmailNoTokenCheck(email)

//                signUpCheckDialogFunction()
            } else {
                Toast.makeText(activity, "이메일 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
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
                if (checkEmail()) {
                    val email = viewBinding.editEmail.text.toString()
                    Log.d("입력받은 이메일",email)
                    SignUpService(this).tryGetUserEmailNoTokenCheck(email)

//                signUpCheckDialogFunction()
                } else {
                    Toast.makeText(activity, "이메일 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    //이메일이 형식에 맞는지 확인하는 메소드
    fun checkEmail():Boolean{
        var email = viewBinding.editEmail.text.toString().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞는지 확인
        if (p) {
            //이메일 형태가 정상일 경우
            viewBinding.tvEmailFormatError.isGone = true
            return true
        } else {
            //정상이 아니면 다시 에러 메시지 보이게
            viewBinding.tvEmailFormatError.isVisible = true
            return false
        }
    }

    //입력한 이메일로 가입할건지 물어보는 메소드
    private fun signUpCheckDialogFunction(){
        val logoutDialog = Dialog(requireContext())
        logoutDialog.setContentView(R.layout.dialog_custom5)
        logoutDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutDialog.findViewById<TextView>(R.id.dialog5_btn_cancel).setOnClickListener {
            // dialog 내림
            logoutDialog.dismiss()
        }

        val logoutBtn=logoutDialog.findViewById<TextView>(R.id.dialog5_btn_access)
        logoutBtn.setOnClickListener {
            logoutDialog.dismiss()

            val bundle = Bundle()
            bundle.putString("email", viewBinding.editEmail.text.toString())
            val signUpPswdFragment = SignUpPswdFragment()
            signUpPswdFragment.arguments = bundle

            val fm = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()

            transaction
                .replace(R.id.sign_up_frm, signUpPswdFragment)
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

        logoutDialog.show()
    }

    //입력한 이메일이 이미 가입된 이메일임을 보여주는 메소드
    private fun alreadySignUpDialogFunction(){
        val logoutDialog = Dialog(requireContext())
        logoutDialog.setContentView(R.layout.dialog_custom2)
        logoutDialog.findViewById<TextView>(R.id.dialog2_title).text = "이미 존재하는 메일 주소입니다.\n다시 시도해주세요."
        logoutDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutDialog.findViewById<TextView>(R.id.dialog2_btn_ok).setOnClickListener {
            // dialog 내림
            logoutDialog.dismiss()
        }
        logoutDialog.show()
    }

    override fun onPostSignUpSuccess(response: BaseResponse) {}

    override fun onPostSignUpFailure(message: String) {}

    override fun onPostSignInSuccess(response: TokenResponse) {}

    override fun onPostSignInWrong(message: String) {}

    override fun onPostSignInFailure(message: String) {}

    override fun onPostChangePasswordSuccess(response: BaseResponse) {}

    override fun onPostChangePasswordFailure(message: String) {}

    override fun onGetUserEmailCheckExist(response: UserEmailCheckResponse) {}

    override fun onGetUserEmailCheckNotExist(message: String) {}

    override fun onGetUserEmailCheckFailure(message: String) {}

    //중복됨
    override fun onGetUserEmailCheckNoTokenExist(response: UserEmailCheckNoTokenResponse) {
        Log.d("이메일 중복확인", "중복됨")
        alreadySignUpDialogFunction()
    }

    //중복 없음
    override fun onGetUserEmailCheckNoTokenNotExist(message: String) {
        Log.d("이메일 중복확인", "중복없음")
        signUpCheckDialogFunction()
    }

    //통신실패
    override fun onGetUserEmailCheckNoTokenFailure(message: String) {
        Log.d("이메일 중복확인", "오류")
    }
}