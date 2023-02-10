package com.recordOfMemory.src.daybook

import android.R.attr.bitmap
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseActivity
import com.recordOfMemory.databinding.ActivityDaybookWritingBinding
import com.recordOfMemory.src.daybook.retrofit.models.DaybookToWriting
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
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
	private val sdfMini = SimpleDateFormat("yy.MM.dd", Locale.KOREA) //날짜 포맷

	private var screenType:String=""
	private var recordId:Int=0;
	private lateinit var imageUri:Uri
	private lateinit var item : GetDiary2Response

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		screenType= intent.getStringExtra("screen_type").toString() //creat냐 update냐에 따라 전달받은게 다름
		if(screenType=="update"){ //수정시에, recordId, date(string), title, content
			val itemGet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				intent.getSerializableExtra("item", DaybookToWriting::class.java)!!
			} else {
				intent.getSerializableExtra("item") as DaybookToWriting
			}
			//println(item)
			recordId=itemGet.recordId
			binding.daybookWritingDiaryTitle.text=itemGet.diaryTitle
			binding.daybookWritingWriteTime.text = itemGet.date
			binding.daybookWritingTitle.setText(itemGet.title)
			binding.daybookWritingContent.setText(itemGet.content)
			if(!itemGet.imgUrl.isNullOrEmpty()){ //이미지가 존재하면 추가해두기
				Glide.with(this).load(itemGet.imgUrl).into(binding.daybookWritingImage)
				binding.daybookWritingFr.visibility=View.VISIBLE
			}

//			var imageUri=intent.getStringExtra("imageUri")
//			if(!imageUri.isNullOrEmpty()){
//				val img= Uri.parse(imageUri)
//				binding.daybookWritingImage.setImageURI(img)
//				binding.daybookWritingFr.visibility=View.VISIBLE
//			}

		}else{// create :  새로 일기를 쓸 때

			//넘어오면서 다이어리 이름 세팅해주세요.<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			binding.daybookWritingWriteTime.text =
				sdfFull.format(System.currentTimeMillis()) //오늘 날짜로 기본 세팅
		}


		binding.daybookWritingClickCalendarIcon.setOnClickListener { //날짜 수정
			changeDate()
		}

		binding.daybookWritingIvComplete.setOnClickListener { //완료
			//제목 있는지 체크
			if (checkTitle()) {
				//				val title=binding.daybookWritingTitle.text //일기 타이틀 (다이어리 타이틀 나중에 추가하자)
//				val content=binding.daybookWritingContent.text
//				val date = binding.daybookWritingWriteTime.text
//				val writer:String = if(screenType=="create"){
//					"카리"
//				}else item.writer
//
//				var itemSend= GetDiary2Response(itemId = "99", title = "$title", content = "$content", date = "$date",writer = writer,
//					imgUrl = "")
//				intent.putExtra("item",itemSend)
//
//				if(binding.daybookWritingFr.visibility==View.VISIBLE){
//					intent.putExtra("imageUri",imageUri.toString())
//				}

				//데이터 통신 끝나고 일기 화면으로 돌아갈 때.
				if(screenType=="update"){
					// 데이터 저장하고 일기 화면으로 돌아가기
					val intent=Intent(this,DaybookActivity::class.java)
					intent.putExtra("recordId",recordId.toString())
					setResult(RESULT_OK,intent)
					finish()
					startActivity(intent)
				}
			}

		}

		binding.daybookWritingIvBack.setOnClickListener {  // 뒤로가기
			//뒤로가기 눌렀을 때 경고창을 띄우기?
			onBackPressed()
		}

		binding.daybookWritingAlbum.isEnabled = binding.daybookWritingFr.visibility==View.GONE
		binding.daybookWritingAlbum.setOnClickListener { //사진 추가
			if(binding.daybookWritingFr.visibility==View.GONE){ //이미 추가된 사진이 없을 때만 사진 추가
				chooseCameraOrAlbumDialogFunction()
			}
		}
		binding.daybookWritingDeleteBtn.setOnClickListener { //사진 삭제 (그냥 화면에서만 없애자)
			binding.daybookWritingFr.visibility=View.GONE
			binding.daybookWritingAlbum.isEnabled=true
		}
	}

	override fun onBackPressed() {
		if(binding.daybookWritingTitle.text.toString().isNotEmpty() || binding.daybookWritingContent.text.toString().isNotEmpty()){
			backPressedDialogFunction()
		}else{
			super.onBackPressed()
		}
	}

	private fun backPressedDialogFunction(){
		val backPressedDialog= Dialog(this)
		backPressedDialog.setContentView(R.layout.dialog_custom)
		backPressedDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

		backPressedDialog.findViewById<TextView>(R.id.dialog1_title).text="작성중인 내용이 있습니다.\n취소하고 이전 페이지로\n이동하시겠습니까?"
		val cancelBtn = backPressedDialog.findViewById<TextView>(R.id.dialog1_btn_cancel)
		cancelBtn.text="취소하기"
		cancelBtn.setOnClickListener {
			backPressedDialog.dismiss()
		}
		val outBtn = backPressedDialog.findViewById<TextView>(R.id.dialog1_btn_delete)
		outBtn.text="나가기"
		outBtn.setOnClickListener {
			super.onBackPressed()
		}
		backPressedDialog.show()
	}

	private fun chooseCameraOrAlbumDialogFunction(){
		val chooseCameraOrAlbumDialog = Dialog(this)
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
					val selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI )  //아직까진 굴러감
					binding.daybookWritingImage.setImageBitmap(selectedImageBitmap) //아직까진 굴러감. 그냥 아래꺼 쓸까..
					//binding.daybookWritingImage.setImageURI(contentURI)

					binding.daybookWritingFr.visibility= View.VISIBLE
					binding.daybookWritingAlbum.isEnabled=false

//					if (contentURI != null) {
//						imageUri=contentURI
//					}
					//imageBitmap=selectedImageBitmap
					imageUri=getImageUri(this,selectedImageBitmap)
				}catch (e:IOException){
					e.printStackTrace()
					Toast.makeText(this, "Failed to load image from gallery", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri {
		val bytes = ByteArrayOutputStream()
		inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
		val path = MediaStore.Images.Media.insertImage(inContext?.getContentResolver(), inImage, "Title" + " - " + Calendar.getInstance().getTime(), null)
		return Uri.parse(path)
	}

	private val cameraLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
			if (it.resultCode == RESULT_OK && it.data!=null){
				try {
					val thumbNail: Bitmap = it!!.data!!.extras?.get("data") as Bitmap
					binding.daybookWritingImage.setImageBitmap(thumbNail) // 이미지 연결
					binding.daybookWritingFr.visibility= View.VISIBLE
					binding.daybookWritingAlbum.isEnabled=false
					imageUri=getImageUri(this,thumbNail)
					
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
		grantResults: IntArray,
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
			getImageAfterPermission(requestCode)
		}else{// 거부당했으면 커스텀 dialog를 통해 환경설정가서 하게 하자
			showDialogToGetPermission(requestCode)
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

	private fun showDialogToGetPermission(requestCode: Int) { // dialog 수정할 것
		val accessDialog = Dialog(this)
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
				Uri.fromParts("package", packageName, null))
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			accessDialog.dismiss()
		}
		accessDialog.show()
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