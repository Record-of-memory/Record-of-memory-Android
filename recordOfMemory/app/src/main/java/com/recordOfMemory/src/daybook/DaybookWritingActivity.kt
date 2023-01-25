package com.recordOfMemory.src.daybook

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseActivity
import com.recordOfMemory.databinding.ActivityDaybookWritingBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DaybookWritingActivity :
	BaseActivity<ActivityDaybookWritingBinding>(ActivityDaybookWritingBinding::inflate) {

	val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
	val CAMERA_PERMISSION_REQUEST = 100
	val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
	val STORAGE_PERMISSION_REQUEST = 200


	private val sdfFull = SimpleDateFormat("yyyy.MM.dd. (E)", Locale.KOREA) //날짜 포맷
	private val sdfMini = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA) //날짜 포맷

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.daybookWritingWriteTime.text =
			sdfFull.format(System.currentTimeMillis()) //오늘 날짜로 기본 세팅

		binding.daybookWritingClickCalendarIcon.setOnClickListener { //날짜 수정
			changeDate()
		}

		binding.daybookWritingIvComplete.setOnClickListener { //완료
			//제목 있는지 체크
			if (checkTitle()) {
				// 데이터 저장하고 일기 화면으로 돌아가기
				Toast.makeText(this,"제목 입력함",Toast.LENGTH_SHORT).show()
			}
		}

		binding.daybookWritingIvBack.setOnClickListener {  // 뒤로가기
			//뒤로가기 눌렀을 때 경고창을 띄우기?
		}

		binding.daybookWritingAlbum.setOnClickListener { //사진 추가
			// 앨범 or 사진 선택 (dialog) 임시

			AlertDialog.Builder(this)
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
		binding.daybookWritingDeleteBtn.setOnClickListener { //사진 삭제 (그냥 화면에서만 없애자)
			binding.daybookWritingFr.visibility=View.GONE
		}
	}

	private fun changeDate() {
		val cal = Calendar.getInstance()    //캘린더뷰 만들기
		val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
			var date = sdfMini.parse("${year}.${month + 1}.${dayOfMonth}")
			var dateString = sdfFull.format(date)

			binding.daybookWritingWriteTime.text = dateString

		}
		DatePickerDialog(this,
			dateSetListener,
			cal.get(Calendar.YEAR),
			cal.get(Calendar.MONTH),
			cal.get(Calendar.DAY_OF_MONTH)).show()
	}

	private fun checkTitle(): Boolean {
		//제목이 비어있으면 경고 dialog 띄워주기
		if (binding.daybookWritingTitle.text.toString().isNullOrEmpty()) {
			titleDialogFunction()
			return false
		}
		return true
	}

	private fun titleDialogFunction() {
		val titleDialog = Dialog(this)
		titleDialog.setContentView(R.layout.dialog_custom2)
		titleDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		titleDialog.findViewById<TextView>(R.id.dialog2_title).text = "일기 제목을 입력해주세요."
		titleDialog.findViewById<TextView>(R.id.dialog2_btn_ok).setOnClickListener {
			titleDialog.dismiss()
		}

		titleDialog.show()
	}


	private val galleryLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
			if (it.resultCode == RESULT_OK && it.data!=null){
				val contentURI= it.data!!.data
				try{
//					val selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI )  //아직까진 굴러감
//					binding.daybookWritingImage.setImageBitmap(selectedImageBitmap) //아직까진 굴러감. 그냥 아래꺼 쓸까..
					binding.daybookWritingImage.setImageURI(contentURI)
					binding.daybookWritingFr.visibility= View.VISIBLE
				}catch (e:IOException){
					e.printStackTrace()
					Toast.makeText(this, "Failed to load image from gallery", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private val cameraLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
			if (it.resultCode == RESULT_OK && it.data!=null){
				try {
					val thumbNail: Bitmap = it!!.data!!.extras?.get("data") as Bitmap
					binding.daybookWritingImage.setImageBitmap(thumbNail) // 이미지 연결
					binding.daybookWritingFr.visibility= View.VISIBLE
				} catch (e: IOException) {
					e.printStackTrace()
					Toast.makeText(this, "Failed to take photo from Camera", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private fun checkPermission(permissions: Array<String>, permissionRequestNumber:Int){
		val permissionResult = ActivityCompat.checkSelfPermission(this, permissions[0])
		if(permissionResult == PackageManager.PERMISSION_GRANTED){
			//권한이 잘 부여되었을 때
			getImageAfterPermission(permissionRequestNumber)
		}else{
			// 처음봤을때 띄워줌
			ActivityCompat.requestPermissions(this, permissions, permissionRequestNumber) //권한을 요청함
		}
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
			getImageAfterPermission(requestCode)
		}else{// 거부당했으면 커스텀 dialog를 통해 환경설정가서 하게 하자
			showDialogToGetPermission()
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

	private fun showDialogToGetPermission() { // dialog 수정할 것
		val builder = AlertDialog.Builder(this)
		builder.setTitle("Permisisons request")
			.setMessage("We need the location permission for some reason. " +
					"You need to move on Settings to grant some permissions")

		builder.setPositiveButton("OK") { dialogInterface, i ->
			val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.fromParts("package", packageName, null))
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)   // 6
		}
		builder.setNegativeButton("Later") { dialogInterface, i ->
			// ignore
		}
		val dialog = builder.create()
		dialog.show()
	}

	override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
		if (event?.action == MotionEvent.ACTION_DOWN) {
			val v = currentFocus
			if (v is EditText) {
				val outRect = Rect()
				v.getGlobalVisibleRect(outRect)
				if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
					v.clearFocus()
					val imm: InputMethodManager =
						getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
				}
			}
		}
		return super.dispatchTouchEvent(event)
	}
}