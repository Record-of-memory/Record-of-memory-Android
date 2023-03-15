package kr.co.app.recordOfMemory.src.main.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.databinding.FragmentMyPageEditPasswordBinding
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.MyPageEditPasswordInterface
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.MyPageEditPasswordService
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.PostChangePasswordRequest
import kr.co.app.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import kr.co.app.recordOfMemory.src.main.signUp.models.TokenResponse
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.SignUpService

import kr.co.app.recordOfMemory.src.splash.SplashActivity
import java.util.regex.Pattern


class MyPageEditPasswordFragment() : BaseFragment<FragmentMyPageEditPasswordBinding>(FragmentMyPageEditPasswordBinding::bind,
	R.layout.fragment_my_page_edit_password),MyPageEditPasswordInterface ,
	GetRefreshTokenInterface {
	lateinit var myPageEditFragment: MyPageEditFragment
	constructor(myPageEditFragment: MyPageEditFragment):this() {
		this.myPageEditFragment = myPageEditFragment
	}

	private var prevPassword:String?=null
	private var newPassword1:String?=null
	private var newPassword2:String?=null

	var statusCode = 1301
	var request : Any = ""

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val fm = requireActivity().supportFragmentManager

		binding.passwordBack.setOnClickListener { //뒤로 가기
			fm.popBackStack()
		}

		binding.passwordCompleteBtn.setOnClickListener { //완료
			//데이터 처리
			if(checkValidation()){
				//백으로 보내서 체크 후에 화면 전환
				statusCode=1303
				val postChangePasswordRequest=PostChangePasswordRequest(oldPassword = prevPassword.toString(), newPassword = newPassword2.toString())
				request=postChangePasswordRequest
				showLoadingDialog(requireContext())
				MyPageEditPasswordService(this).tryPostChangePassword(postChangePasswordRequest)

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

	override fun onPostChangePasswordSuccess(response: BaseResponse) {
		dismissLoadingDialog()
		Log.d("성공","${response.information.message}")
		//화면 전환
		val fm = requireActivity().supportFragmentManager
		fm.popBackStack()
	}

	override fun onPostChangePasswordFailure(message: String) {
		dismissLoadingDialog()
		if(message == "refreshToken") {
			val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(
                ApplicationClass.X_REFRESH_TOKEN, "").toString()
			SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
		}
		// 토큰 갱신 문제가 아닐 경우
		else {
			Log.d("실패",message)
			Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
		}
	}

	override fun onPostRefreshSuccess(response: TokenResponse) {
		dismissLoadingDialog()
		val editor = ApplicationClass.sSharedPreferences.edit()
		editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
		editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
		editor.apply()

		showLoadingDialog(requireContext())
		when (statusCode) {
			1303 -> MyPageEditPasswordService(this).tryPostChangePassword(request as PostChangePasswordRequest)
		}
	}

    override fun onPostRefreshFailure(message: String) {
        dismissLoadingDialog()
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.remove(ApplicationClass.X_ACCESS_TOKEN)
        editor.remove(ApplicationClass.X_REFRESH_TOKEN)
        editor.apply()

        val intent = Intent(context, SplashActivity::class.java)
		requireActivity().finishAffinity()
		startActivity(intent)
    }
}