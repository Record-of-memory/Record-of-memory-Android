package com.recordOfMemory.src.main.myPage

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentMyPageEditBinding
import com.recordOfMemory.src.main.MainActivity
import java.io.IOException


class MyPageEditFragment: BaseFragment<FragmentMyPageEditBinding>(FragmentMyPageEditBinding::bind, R.layout.fragment_my_page_edit) {

	val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
	val CAMERA_PERMISSION_REQUEST = 100
	val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
	val STORAGE_PERMISSION_REQUEST = 200

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
			AlertDialog.Builder(requireActivity())
				.setTitle("앨범과 카메라 중에 선택하세요")
				.setPositiveButton("앨범") { _, _ ->
					checkPermission(STORAGE_PERMISSION, STORAGE_PERMISSION_REQUEST)
				}
				.setNegativeButton("카메라") { _, _ ->
					checkPermission(CAMERA_PERMISSION, CAMERA_PERMISSION_REQUEST)
				}
				.create()
				.show()
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

	private val cameraRequestPermissionLauncher = registerForActivityResult(
		ActivityResultContracts.RequestPermission()
	) { isGranted ->
		if (isGranted) {
			getImageAfterPermission(CAMERA_PERMISSION_REQUEST)
		} else {
			showDialogToGetPermission()
		}
	}

	private val storageRequestMultiplePermissions =  registerForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions()
	){ resultMap ->
		val isAllGranted = STORAGE_PERMISSION.all { e -> resultMap[e] == true }
		if (isAllGranted) {
			getImageAfterPermission(STORAGE_PERMISSION_REQUEST)
		} else {
			showDialogToGetPermission()
		}
	}

	private fun checkPermission(permissions: Array<String>, permissionRequestNumber:Int){
		val permissionResult = ActivityCompat.checkSelfPermission(requireActivity(), permissions[0])
		if(permissionResult == PackageManager.PERMISSION_GRANTED){
			//권한이 잘 부여되었을 때
			getImageAfterPermission(permissionRequestNumber)
		}else{
			if(permissionRequestNumber==CAMERA_PERMISSION_REQUEST){
				cameraRequestPermissionLauncher.launch(Manifest.permission.CAMERA)
			}else{
				storageRequestMultiplePermissions.launch(STORAGE_PERMISSION)
			}
		}
	}

	private fun showDialogToGetPermission() { // dialog 수정할 것
		val builder = AlertDialog.Builder(requireActivity())
		builder.setTitle("Permisisons request")
			.setMessage("We need the location permission for some reason. " +
					"You need to move on Settings to grant some permissions")

		builder.setPositiveButton("OK") { dialogInterface, i ->
			val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.fromParts("package", activity?.packageName, null))
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)   // 6
		}
		builder.setNegativeButton("Later") { dialogInterface, i ->
			// ignore
		}
		val dialog = builder.create()
		dialog.show()
	}

	private val galleryLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
			if (it.resultCode == AppCompatActivity.RESULT_OK && it.data!=null){
				val contentURI= it.data!!.data
				try{
//					val selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI )  //아직까진 굴러감
//					binding.daybookWritingImage.setImageBitmap(selectedImageBitmap) //아직까진 굴러감. 그냥 아래꺼 쓸까..
					binding.mypageEditPersonImg.setImageURI(contentURI)
				}catch (e: IOException){
					e.printStackTrace()
					Toast.makeText(context, "Failed to load image from gallery", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private val cameraLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
			if (it.resultCode == AppCompatActivity.RESULT_OK && it.data!=null){
				try {
					val thumbNail: Bitmap = it!!.data!!.extras?.get("data") as Bitmap
					binding.mypageEditPersonImg.setImageBitmap(thumbNail) // 이미지 연결
				} catch (e: IOException) {
					e.printStackTrace()
					Toast.makeText(context, "Failed to take photo from Camera", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private fun getImageAfterPermission(permissionRequestNumber: Number){
		if(permissionRequestNumber==CAMERA_PERMISSION_REQUEST){ //카메라
			val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //이미지 사진 찍는 곳으로 넘어감
			cameraLauncher.launch(intent)
		}else if(permissionRequestNumber==STORAGE_PERMISSION_REQUEST){ //앨범
			val galleryIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
			galleryLauncher.launch(galleryIntent)
		}
	}


	private fun deleteAccountDialogFunction(){
		val deleteAccountDialog = Dialog(requireContext())
		deleteAccountDialog.setContentView(R.layout.dialog_mypage_delete_account)
		deleteAccountDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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