package kr.co.app.recordOfMemory.src.main.myPage

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.databinding.FragmentMyPageEditBinding
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.MyPageEditInterface
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.MyPageEditService
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import kr.co.app.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import kr.co.app.recordOfMemory.src.main.signUp.models.TokenResponse
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.SignUpService
import kr.co.app.recordOfMemory.src.splash.SplashActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.util.*


class MyPageEditFragment(): BaseFragment<FragmentMyPageEditBinding>(FragmentMyPageEditBinding::bind, R.layout.fragment_my_page_edit),MyPageEditInterface,
	GetRefreshTokenInterface {
	lateinit var myPageFragment: MyPageFragment
	constructor(myPageFragment: MyPageFragment):this() {
		this.myPageFragment = myPageFragment
	}

	val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
	val CAMERA_PERMISSION_REQUEST = 100
	val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
	val STORAGE_PERMISSION_REQUEST = 200

	private var changeImg=false;
	var statusCode = 1201
	var request : Any = ""
	var nickname = ""
	var imgUrl : MultipartBody.Part? = null


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val fm = requireActivity().supportFragmentManager
		val transaction: FragmentTransaction = fm.beginTransaction()

		// 이미지 세팅
		val userImg=arguments?.getString("userImg")
		if(userImg.isNullOrEmpty()){
			binding.mypageEditPersonImg.setImageResource(R.drawable.icn_user)
		}else{
			Glide.with(this).load(userImg)
				.into(binding.mypageEditPersonImg)
		}

		binding.mypageEditBack.setOnClickListener { //뒤로가기 - 다른 방법이 있는지 확인할 것
			fm.popBackStack()
		}

		binding.mypageEditChangePassword.setOnClickListener { //비밀번호 변경
			transaction
				.replace(R.id.main_frm, MyPageEditPasswordFragment(this))
				.addToBackStack(null)
				.commit()
			transaction.isAddToBackStackAllowed
		}

		binding.mypageEditDeleteAccount.setOnClickListener { //계정 탈퇴하기
			deleteAccountDialogFunction()
		}

		binding.mypageEditImageBtn.setOnClickListener {
			//이미지 수정
			chooseCameraOrAlbumDialogFunction()
		}

		binding.mypageEditCompleteBtn.setOnClickListener { //완료
			if(checkName() || changeImg){ //이름과 이미지 중에 바뀐 것이 있으면
				//저장하고
				nickname = binding.mypageEditBoxName.text.toString()

//			val jsonObject = JSONObject(
//			val jsonBody = jsonObject.toRequestBody("application/json".toMediaTypeOrNull())
//				"{\"nickname\":\"${nickname}\"}").toString()

				// 마이페이지로 넘어가기
				MyPageEditService(this).tryPatchUsers(imgUrl, nickname)
			}
			else {
				showCustomToast("변경된 내용이 없습니다.")
			}
		}
	}

	private fun chooseCameraOrAlbumDialogFunction(){
		val chooseCameraOrAlbumDialog = Dialog(requireContext())
		chooseCameraOrAlbumDialog.setContentView(R.layout.dialog_custom3)
		chooseCameraOrAlbumDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		chooseCameraOrAlbumDialog.findViewById<TextView>(R.id.dialog3_btn_camera).setOnClickListener {
			checkPermission(CAMERA_PERMISSION, CAMERA_PERMISSION_REQUEST)
			chooseCameraOrAlbumDialog.dismiss()
		}
		chooseCameraOrAlbumDialog.findViewById<TextView>(R.id.dialog3_btn_album).setOnClickListener {
			checkPermission(STORAGE_PERMISSION, STORAGE_PERMISSION_REQUEST)
			chooseCameraOrAlbumDialog.dismiss()
		}
		chooseCameraOrAlbumDialog.show()
	}

	private val cameraRequestPermissionLauncher = registerForActivityResult(
		ActivityResultContracts.RequestPermission()
	) { isGranted ->
		if (isGranted) {
			getImageAfterPermission(CAMERA_PERMISSION_REQUEST)
		} else {
			showDialogToGetPermission(CAMERA_PERMISSION_REQUEST)
		}
	}

	private val storageRequestMultiplePermissions =  registerForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions()
	){ resultMap ->
		val isAllGranted = STORAGE_PERMISSION.all { e -> resultMap[e] == true }
		if (isAllGranted) {
			getImageAfterPermission(STORAGE_PERMISSION_REQUEST)
		} else {
			showDialogToGetPermission(STORAGE_PERMISSION_REQUEST)
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

	private fun showDialogToGetPermission(requestCode: Int) {
		val accessDialog = Dialog(requireContext())
		accessDialog.setContentView(R.layout.dialog_custom4)
		accessDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

		var titleText=""
		var subText=""
		if(requestCode==100){
			titleText = "'우리기억'이 카메라에\n 접근하려 합니다"
			subText = "포스트 작성 / 프로필 변경을 위해\n사용자의 카메라에 접근하려 합니다."
		}else{
			titleText = "'우리기억'이 사용자의\n 사진에 접근하려 합니다"
			subText="포스트 작성 / 프로필 변경을 위해\n사용자의 앨범에 접근하려 합니다."
		}

		accessDialog.findViewById<TextView>(R.id.dialog4_title).text=titleText
		accessDialog.findViewById<TextView>(R.id.dialog4_subtitle).text=subText
		val cancelBtn = accessDialog.findViewById<TextView>(R.id.dialog4_btn_cancel)
		cancelBtn.text="취소"
		cancelBtn.setOnClickListener { accessDialog.dismiss() }
		val accessBtn = accessDialog.findViewById<TextView>(R.id.dialog4_btn_access)
		accessBtn.text="허용"
		accessBtn.setOnClickListener {
			val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.fromParts("package", activity?.packageName, null))
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			accessDialog.dismiss()
		}
		accessDialog.show()
	}

	private val galleryLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
			if (it.resultCode == AppCompatActivity.RESULT_OK && it.data!=null){
				val contentURI= it.data!!.data!!
				try{
//					val selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI )  //아직까진 굴러감
//					binding.daybookWritingImage.setImageBitmap(selectedImageBitmap) //아직까진 굴러감. 그냥 아래꺼 쓸까..
					binding.mypageEditPersonImg.setImageURI(contentURI)
					changeImg=true// 이미지가 새것인지 체크

					val filePath = getFilePath(contentURI)

					// Get the image file
					val file = File(filePath)
					val requestFile =
						file.asRequestBody("image/*".toMediaTypeOrNull())

					imgUrl = requestFile.let{ it1->
						MultipartBody.Part.createFormData("profileImg", file.name,
							it1
						)
					}
				}catch (e: IOException){
					e.printStackTrace()
					Toast.makeText(context, "Failed to load image from gallery", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private val cameraLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
			if (it.resultCode == AppCompatActivity.RESULT_OK && it.data != null) {
				try {
					val img = it!!.data!!.extras?.get("data") as Bitmap

					val contentValues = ContentValues().apply {
						put(
							MediaStore.Images.Media.DISPLAY_NAME,
							"IMG - ${Calendar.getInstance().time}"
						)
						put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
						put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
						put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
					}

					val contentURI = requireActivity().contentResolver.insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						contentValues
					)

					if (contentURI != null) {
						try {
							requireActivity().contentResolver.openOutputStream(contentURI)
								.use { outputStream ->
									img.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
								}

							val filePath = getFilePath(contentURI)
							// do something with the file path here

							// Get the image file
							val file = File(filePath)
							val requestFile =
								file.asRequestBody("image/*".toMediaTypeOrNull())

							imgUrl = MultipartBody.Part.createFormData(
								"profileImg", file.name,
								requestFile
							)
						} catch (e: Exception) {
							e.printStackTrace()
							Toast.makeText(
								context,
								"Failed to take photo from Camera",
								Toast.LENGTH_SHORT
							).show()
						}
					}
					val thumbNail: Bitmap = it.data!!.extras?.get("data") as Bitmap
					binding.mypageEditPersonImg.setImageBitmap(thumbNail) // 이미지 연결
					changeImg = true // 이미지가 새것인지 체크
				} catch (e: IOException) {
					e.printStackTrace()
					showCustomToast("Failed to take photo from Camera")
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
			deleteAccountDialog.dismiss()

			statusCode=1203
			MyPageEditService(this).tryDeleteUsers()
		}

		deleteAccountDialog.show()
	}

	private fun checkName():Boolean{
		val userNewName=binding.mypageEditBoxName.text.toString()
		return userNewName.isNotEmpty()
	}

	override fun onDeleteUsersSuccess(response: DeleteUsersResponse) {
		Log.d("성공","${response.information.message}")
		//화면은 스플래시 화면으로
		startActivity(Intent(context, SplashActivity::class.java))
	}

	override fun onDeleteUsersFailure(message: String) {
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

	override fun onPatchUsersSuccess(response: BaseResponse) {
		val fm = requireActivity().supportFragmentManager

		fm.popBackStack()

	}

	override fun onPatchUsersFailure(message: String) {
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
		val editor = ApplicationClass.sSharedPreferences.edit()
		editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
		editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
		editor.apply()

		when(statusCode) {
			1203 -> MyPageEditService(this).tryDeleteUsers()
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

	private fun getFilePath(contentUri: Uri): String {
		val cursor = requireActivity().contentResolver.query(contentUri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)

		cursor?.use {
			if (it.moveToFirst()) {
				val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
				return it.getString(columnIndex)
			}
		}

		return ""
	}
}