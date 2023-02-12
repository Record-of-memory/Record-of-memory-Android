package com.recordOfMemory.src.main.myPage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentMyPageEditPasswordBinding
import com.recordOfMemory.src.main.signUp.models.TokenResponse
import com.recordOfMemory.src.main.signUp.models.UserEmailCheckNoTokenResponse
import com.recordOfMemory.src.main.signUp.models.UserEmailCheckResponse
import com.recordOfMemory.src.main.signUp.retrofit.SignUpFragmentInterface
import java.util.regex.Pattern


class MyPageEditPasswordFragment : BaseFragment<FragmentMyPageEditPasswordBinding>(FragmentMyPageEditPasswordBinding::bind,
	R.layout.fragment_my_page_edit_password), SignUpFragmentInterface {

	private var prevPassword:String?=null
	private var newPassword1:String?=null
	private var newPassword2:String?=null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val fm = requireActivity().supportFragmentManager
		val transaction: FragmentTransaction = fm.beginTransaction()

		binding.passwordBack.setOnClickListener { //뒤로 가기
			fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
			transaction
				.replace(R.id.main_frm,MyPageEditFragment())
				.addToBackStack(null)
				.commit()
			transaction.isAddToBackStackAllowed
		}

		binding.passwordCompleteBtn.setOnClickListener { //완료
			//데이터 처리
			if(checkValidation()){
				//백으로 보내서 체크 후에 화면 전환


				//화면 전환
				fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
				transaction
					.replace(R.id.main_frm, MyPageEditFragment()) //수정 화면으로 가게
					.addToBackStack(null)
					.commitAllowingStateLoss()
				transaction.isAddToBackStackAllowed
			}
		}


	}

	private fun checkValidation():Boolean{
		binding.passwordCheckError.visibility=View.INVISIBLE
		binding.passwordNewError.visibility=View.INVISIBLE
		binding.passwordAgainError.visibility=View.INVISIBLE

		prevPassword=binding.passwordCheck.text.toString()
		newPassword1=binding.passwordNew.text.toString()
		newPassword2=binding.passwordAgain.text.toString()

		var checkError=false

		if(prevPassword.isNullOrEmpty() || newPassword1.isNullOrEmpty() ||newPassword2.isNullOrEmpty()){
			Toast.makeText(context,"빈칸을 채워주세요.",Toast.LENGTH_SHORT).show()
			return false
		}else{
			if(prevPassword==newPassword1){
				binding.passwordNewError.text="새로운 비밀번호를 입력해주세요"
				binding.passwordNewError.visibility=View.VISIBLE
				checkError=true
			}
			if(!checkPasswordPattern(newPassword1!!)){
				binding.passwordNewError.text="영문, 숫자 포함 8~20자 이내로 설정해주세요"
				binding.passwordNewError.visibility=View.VISIBLE
				checkError=true
			}
			if(newPassword1!=newPassword2){
				binding.passwordAgainError.visibility=View.VISIBLE
				checkError=true
			}
		}

		if(checkError) return false
		return true
	}

	private fun checkPasswordPattern(newPassword1:String):Boolean{
		val PW = "^(?=.*[A-Za-z])(?=.*[0-9]).{8,20}$"
		val pwPattern = Pattern.compile(PW)
		val matcher=pwPattern.matcher(newPassword1)

		if(matcher.matches()){
			return true
		}
		return false
	}

	override fun onPostSignUpSuccess(response: BaseResponse) {}

	override fun onPostSignUpFailure(message: String) {}

	override fun onPostSignInSuccess(response: TokenResponse) {}

	override fun onPostSignInWrong(message: String) {}

	override fun onPostSignInFailure(message: String) {}

	override fun onPostChangePasswordSuccess(response: BaseResponse) {
		Toast.makeText(activity, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show()
		//화면 전환
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.main_frm, MyPageFragment())
			.commitAllowingStateLoss()
	}

	override fun onPostChangePasswordFailure(message: String) {
		if (message == "통신 오류") {
			Toast.makeText(activity, "통신 오류", Toast.LENGTH_SHORT).show()
		} else if (message == "비밀번호 다름"){
			Toast.makeText(activity, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
		}
	}

	override fun onGetUserEmailCheckExist(response: UserEmailCheckResponse) {}

	override fun onGetUserEmailCheckNotExist(message: String) {}

	override fun onGetUserEmailCheckFailure(message: String) {}

	override fun onGetUserEmailCheckNoTokenExist(response: UserEmailCheckNoTokenResponse) {}

	override fun onGetUserEmailCheckNoTokenNotExist(message: String) {}

	override fun onGetUserEmailCheckNoTokenFailure(message: String) {}

}