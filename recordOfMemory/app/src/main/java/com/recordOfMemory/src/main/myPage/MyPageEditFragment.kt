package com.recordOfMemory.src.main.myPage

import android.app.Dialog
import android.os.Bundle

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentMyPageEditBinding
import com.recordOfMemory.src.main.MainActivity


class MyPageEditFragment: BaseFragment<FragmentMyPageEditBinding>(FragmentMyPageEditBinding::bind, R.layout.fragment_my_page_edit) {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.mypageEditBack.setOnClickListener { //뒤로가기 - 다른 방법이 있는지 확인할 것
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.main_frm, MyPageFragment())
				.commitAllowingStateLoss()
		}

		binding.mypageEditChangePassword.setOnClickListener { //비밀번호 변경
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.main_frm, MyPageEditPasswordFragment())
				.commitAllowingStateLoss()
		}

		binding.mypageEditDeleteAccount.setOnClickListener { //계정 탈퇴하기
			deleteAccountDialogFunction()
		}

		binding.mypageEditImageBtn.setOnClickListener {
			//이미지 수정
			Toast.makeText(context,"저장소 넘어가기",Toast.LENGTH_SHORT).show()
		}

		binding.mypageEditCompleteBtn.setOnClickListener { //완료
			// 이름과 이미지 저장하기
			checkName()
			
			//context?.hideKeyboard(view) //넘어가기 전에 키보드 내리기
			(context as MainActivity).supportFragmentManager.beginTransaction()
				.replace(R.id.main_frm, MyPageFragment())
				.commitAllowingStateLoss()
		}


	}

	private fun deleteAccountDialogFunction(){
		val deleteAccountDialog = Dialog(requireContext())
		deleteAccountDialog.setContentView(R.layout.dialog_mypage_delete_account)

		deleteAccountDialog.findViewById<TextView>(R.id.mypage_btn_cancel).setOnClickListener {
			// dialog 내림
			deleteAccountDialog.dismiss()
		}

		deleteAccountDialog.findViewById<TextView>(R.id.mypage_btn_delete).setOnClickListener {
			//탈퇴하기
			Toast.makeText(context, "탈퇴하기", Toast.LENGTH_SHORT).show()
			deleteAccountDialog.dismiss()
		}

		deleteAccountDialog.show()
	}

	private fun checkName():Boolean{
		val userNewName=binding.mypageEditBoxName.text.toString()
		if(userNewName.isEmpty()){
			return false
		}else{
			Toast.makeText(context,"$userNewName",Toast.LENGTH_SHORT).show()
			//데이터 저장하기

			return true
		}
	}

}