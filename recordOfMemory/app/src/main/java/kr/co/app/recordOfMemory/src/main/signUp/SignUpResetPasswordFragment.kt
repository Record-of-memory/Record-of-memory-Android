package kr.co.app.recordOfMemory.src.main.signUp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.databinding.FragmentSignUpResetPasswordBinding
import kr.co.app.recordOfMemory.src.main.signUp.models.PostResetPasswordRequest
import kr.co.app.recordOfMemory.src.main.signUp.models.TokenResponse
import kr.co.app.recordOfMemory.src.main.signUp.models.UserEmailCheckNoTokenResponse
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.SignUpFragmentInterface
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.SignUpService
import java.util.regex.Pattern

class SignUpResetPasswordFragment : BaseFragment<FragmentSignUpResetPasswordBinding>(
    FragmentSignUpResetPasswordBinding::bind, R.layout.fragment_sign_up_reset_password),
    SignUpFragmentInterface {

    private lateinit var viewBinding: FragmentSignUpResetPasswordBinding

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
        viewBinding = FragmentSignUpResetPasswordBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
            //로그인 화면으로 돌아가기
            requireActivity().supportFragmentManager.popBackStack()
        }
        //비밀번호 재설정 메일 받기 버튼 누르면
        viewBinding.nextBtn.setOnClickListener {
            //이메일이 형식에 맞고
            if (checkEmail()) {
                //이메일과 닉네임 모두 빈칸이 아닐 때
                if (viewBinding.editNickname.text.toString().isNotEmpty()) {
                    val postResetPasswordRequest = PostResetPasswordRequest(
                        email = viewBinding.editEmail.text.toString(),
                        nickname = viewBinding.editNickname.text.toString()
                    )
                    showLoadingDialog(requireContext())
                    SignUpService(this).tryPostResetPassword(postResetPasswordRequest)

                } else {
                    //닉네임 칸이 비었을 때
                    Toast.makeText(activity, "닉네임을 작성해주세요.", Toast.LENGTH_LONG).show()
                }
            } else {
                //이메일이 형식에 맞지 않으면 모달 띄우기
                wrongEmailDialogFunction()
            }
        }


    }

    //이메일이 형식에 맞는지 확인하는 메소드
    fun checkEmail():Boolean{
        //이메일 검사 정규식
        val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        var email = viewBinding.editEmail.text.toString().trim() //공백제거
        return Pattern.matches(emailValidation, email) // 서로 패턴이 맞는지 확인
    }

    //입력된 이메일이 틀렸음을 보여주는 메소드
    private fun wrongEmailDialogFunction(){
        val logoutDialog = Dialog(requireContext())
        logoutDialog.setContentView(R.layout.dialog_custom2)
        logoutDialog.findViewById<TextView>(R.id.dialog2_title).text = "이메일 주소를 다시 한 번 확인해주세요."
        logoutDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutDialog.findViewById<TextView>(R.id.dialog2_btn_ok).setOnClickListener {
            // dialog 내림
            logoutDialog.dismiss()
        }
        logoutDialog.show()
    }

    //임시 비밀번호 메일로 전송했음을 보여주는 이메일
    private fun sendNewPasswordDialogFunction(){
        val logoutDialog = Dialog(requireContext())
        logoutDialog.setContentView(R.layout.dialog_custom2)
        logoutDialog.findViewById<TextView>(R.id.dialog2_title).text = "임시 비밀번호가 발송되었습니다."
        logoutDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutDialog.findViewById<TextView>(R.id.dialog2_btn_ok).setOnClickListener {
            // dialog 내림
            logoutDialog.dismiss()
        }
        logoutDialog.show()

        //로그인 화면으로 돌아가기
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onPostSignUpSuccess(response: BaseResponse) {}

    override fun onPostSignUpFailure(message: String) {}

    override fun onPostSignInSuccess(response: TokenResponse) {}

    override fun onPostSignInWrong(message: String) {}

    override fun onPostSignInFailure(message: String) {}

    override fun onPostChangePasswordSuccess(response: BaseResponse) {}

    override fun onPostChangePasswordFailure(message: String) {}

    override fun onGetUserEmailCheckNoTokenExist(response: UserEmailCheckNoTokenResponse) {}

    override fun onGetUserEmailCheckNoTokenNotExist(message: String) {}

    override fun onGetUserEmailCheckNoTokenFailure(message: String) {}

    override fun onPostResetPasswordSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        sendNewPasswordDialogFunction()
    }

    override fun onPostResetPasswordWrong(message: String) {
        dismissLoadingDialog()
        wrongEmailDialogFunction()
        Log.d("임시 비밀번호 전송 실패","이메일 주소, 닉네임 확인 필요")
    }

    override fun onPostResetPasswordFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(activity, "오류 : $message", Toast.LENGTH_LONG).show()
        Log.d("임시 비밀번호 전송 오류","$message")
    }
}