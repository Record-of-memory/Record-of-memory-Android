package com.recordOfMemory.src.main.myPage

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentMyPageEditPasswordBinding
import com.recordOfMemory.src.main.MainActivity

class MyPageEditPasswordFragment : BaseFragment<FragmentMyPageEditPasswordBinding>(FragmentMyPageEditPasswordBinding::bind,
	R.layout.fragment_my_page_edit_password) {

	private var prevPassword:String?=null
	private var newPassword:String?=null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.passwordBack.setOnClickListener { //뒤로 가기
			(context as MainActivity).supportFragmentManager.beginTransaction()
				.replace(R.id.main_frm, MyPageEditFragment())
				.commitAllowingStateLoss()
		}

		binding.passwordCompleteBtn.setOnClickListener { //완료
			//데이터 처리 후 화면 전환

			(context as MainActivity).supportFragmentManager.beginTransaction()
				.replace(R.id.main_frm, MyPageFragment())
				.commitAllowingStateLoss()
		}



		//TODO: 엔터 말고 focus가 바꼈을 때도 작동하도록 할 것
		binding.passwordCheck.setOnEditorActionListener { v, actionId, event ->
			val password = binding.passwordCheck.text.toString()
			if(checkPassword(password)){ //패스워드가 맞으면
				prevPassword=password
				binding.passwordCheckError.visibility=View.INVISIBLE
				false
			}else{
				binding.passwordCheckError.visibility=View.VISIBLE
				true
			}
		}

		binding.passwordNew.setOnEditorActionListener { v, actionId, event ->
			val passwordNewText=binding.passwordNew.text.toString()
			if(checkNewPassword(passwordNewText)){ //새 비밀번호가 타당하면
				newPassword=passwordNewText
				binding.passwordNewError.visibility=View.INVISIBLE
				false
			}else{
				binding.passwordNewError.visibility=View.VISIBLE
				true
			}
		}

		binding.passwordAgain.setOnEditorActionListener { v, actionId, event ->
			val passwordAgainText=binding.passwordAgain.text.toString()
			if(passwordAgainText==newPassword){
				binding.passwordAgainError.visibility=View.INVISIBLE
				false
			}else{
				binding.passwordAgainError.visibility=View.VISIBLE
				true
			}
		}

	}

	private fun checkPassword(password:String):Boolean{
		//패스워드 맞는지 체크 -> 백엔드랑 연결
		return true
	}

	private fun checkNewPassword(passwordNewText : String):Boolean{
		//패스워드 기준 맞는지 로직짜기 -> 백엔드가 해주나?
		return true
	}
}