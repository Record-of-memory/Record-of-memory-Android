package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.recordOfMemory.databinding.FragmentSignUpEmailBinding
import java.util.regex.Pattern

class SignUpEmailFragment : Fragment() {
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
            signUpActivity!!.openFragmentSignUp(2)
        }
        //다음 버튼 누르면
        viewBinding.nextBtn.setOnClickListener {
            if (checkEmail()) {
                signUpActivity!!.openFragmentSignUp(4)
            } else {
                Toast.makeText(activity, "이메일 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //이메일이 형식에 맞는지 확인하는 메소드
    fun checkEmail():Boolean{
        var email = viewBinding.editEmail.text.toString().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞는지 확인
        if (p) {
            //이메일 형태가 정상일 경우
            viewBinding.tvEmailFormatError.visibility = View.INVISIBLE
            return true
        } else {
            //정상이 아니면 다시 에러 메시지 보이게
            viewBinding.tvEmailFormatError.visibility = View.VISIBLE
            return false
        }
    }
}