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
import androidx.fragment.app.FragmentTransaction
import com.recordOfMemory.R
import com.recordOfMemory.databinding.FragmentSignUpPswdBinding
import java.util.regex.Pattern

class SignUpPswdFragment : Fragment() {
    private lateinit var viewBinding: FragmentSignUpPswdBinding
    //비밀번호 검사 정규식
    val passwordValidation = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"

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
        //시작할 땐 editPswd와 editCheckPswd의 입력값이 다름 메시지 가려두기
        viewBinding.tvPswdFormatError.visibility = View.INVISIBLE

        viewBinding.editCheckPswd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            //값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //editPswd와 editCheckPswd의 입력값이 같을 때 다음 활성화
                if (checkSamePassword()) {
                    viewBinding.tvPswdFormatError.visibility = View.INVISIBLE
                } else {
                    viewBinding.tvPswdFormatError.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(3)
        }
        //다음 버튼 누르면
        viewBinding.nextBtn.setOnClickListener {
            //이메일 형식이 올바를 때
            if (checkPassword()) {
                //이메일 형식도 올바르고, 확인 비밀번호와 일치할 때
                if (checkSamePassword()) {
                    val email = arguments?.getString("email")

                    val bundle = Bundle()
                    bundle.putString("password", viewBinding.editPswd.text.toString())
                    bundle.putString("email", email.toString())

                    val signUpNicknameFragment = SignUpNicknameFragment()
                    signUpNicknameFragment.arguments = bundle

                    val fm = requireActivity().supportFragmentManager
                    val transaction: FragmentTransaction = fm.beginTransaction()

                    transaction
                        .replace(R.id.sign_up_frm, signUpNicknameFragment)
                        .addToBackStack(null)
                        .commit()
                    transaction.isAddToBackStackAllowed
//                    signUpActivity!!.openFragmentSignUp(5)
                }
                else {
                    Toast.makeText(activity, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "비밀번호 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //비밀번호 형식이 맞는지 확인 메소드
    fun checkPassword() : Boolean {
        var password = viewBinding.editPswd.text.toString().trim() //공백제거
        return Pattern.matches(passwordValidation, password) // 서로 패턴이 맞는지 확인
    }

    //확인용 비밀번호와 일치하는지 확인 메소드
    fun checkSamePassword() : Boolean {
        return viewBinding.editPswd.text.toString() == viewBinding.editCheckPswd.text.toString()
    }
}