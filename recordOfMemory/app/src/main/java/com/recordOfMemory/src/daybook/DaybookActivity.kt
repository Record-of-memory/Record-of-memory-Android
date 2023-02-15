package com.recordOfMemory.src.daybook

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseActivity
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.ActivityDaybookBinding
import com.recordOfMemory.src.daybook.retrofit.CommentInterface
import com.recordOfMemory.src.daybook.retrofit.CommentService
import com.recordOfMemory.src.daybook.retrofit.DaybookInterface
import com.recordOfMemory.src.daybook.retrofit.DaybookService
import com.recordOfMemory.src.daybook.retrofit.models.*
import com.recordOfMemory.src.main.home.diary2.likes.*
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.MyPageInterface
import com.recordOfMemory.src.main.myPage.retrofit.MyPageService
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import com.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import com.recordOfMemory.src.main.signUp.models.TokenResponse
import com.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService
import com.recordOfMemory.src.splash.SplashActivity
import java.text.SimpleDateFormat
import java.util.*

class DaybookActivity : BaseActivity<ActivityDaybookBinding>(ActivityDaybookBinding::inflate),CommentInterface,
	MyPageInterface, DaybookInterface , LikesInterface, GetRefreshTokenInterface {
	private var commentList = ArrayList<Comment>()
	private lateinit var commentAdapter :CommentAdapter
	private var imageUri:String=""
	private var daybookImageUrl=""
	private val sdfMini = SimpleDateFormat("yy.MM.dd", Locale.KOREA)
	private val sdfFull=SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
	private val sdfFull2 = SimpleDateFormat("yyyy.MM.dd. (E)", Locale.KOREA) //날짜 포맷
	private var recordId:Int =0 // 일기리스트에서 아이디 받아오면 굳이 필요 없는 변수일수도?
	private var cmtNum:Int=0

	private lateinit var userComment:Comment
	var statusCode = 1001
	var request : Any = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//이제 이 부분 필요 없는 내용 아닌가????? ----- 일기리스트에서 넘어올 때,일기 아이디 받아오기
//		recordId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//			intent.getSerializableExtra("recordId", GetRecordResponse::class.java)!!
//		} else {
//			intent.getSerializableExtra("item") as GetRecordResponse
//		}
//		println(item)
		recordId=intent.getStringExtra("recordId").toString().toInt()

		showLoadingDialog(this)
		statusCode=1003
		DaybookService(this).tryGetDaybook(recordId)

		statusCode=1005
		CommentService(this).tryGetComments(recordId)

		statusCode=1007
		MyPageService(this).tryGetUsers()

		statusCode=2000
		LikesService(this).tryCheckLikes(recordId = recordId.toString())


		binding.daybookIvBack.setOnClickListener {
			finish()
		}

		binding.daybookImage.setOnClickListener { //이미지 클릭
			imageDialogFunction()
		}

		binding.daybookBtnSubmit.setOnClickListener {
			var comment=binding.daybookWriteComment
			if(!comment.text.toString().isNullOrEmpty()){
				val commentText=comment.text.toString()

				statusCode = 1009
				val postCommentRequest=PostCommentRequest(recordId = recordId, content = commentText)

				request=postCommentRequest
				Log.d("내용",postCommentRequest.toString())
				showLoadingDialog(this)
				CommentService(this).tryPostComment(postCommentRequest)
			}else{
				commentDialogFunction()
			}
		}

		/* 일기작성자인지 아닌지에 따라 화면이 다르게 보임 */
		binding.daybookIvMore.setOnClickListener { //케밥메뉴 클릭
			miniDialogFunction()
		}

		binding.daybookClickHeartIcon.setOnClickListener {
			showLoadingDialog(this)
			if(binding.daybookClickHeartIcon.isSelected) {
				LikesService(this).tryDeleteLikes(recordId = recordId.toString())
			}
			else {
				LikesService(this).tryPostLikes(PostLikesRequest(recordId = recordId.toString()))
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if(requestCode== 10){
			Toast.makeText(this,data?.getStringExtra("recordId"),Toast.LENGTH_SHORT).show()
			recordId= data?.getStringExtra("recordId")!!.toInt()
		}
	}

	private fun miniDialogFunction(){
		val miniDialog = Dialog(this)
		miniDialog.setContentView(R.layout.dialog_daybook_mini)
		miniDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		miniDialog.window?.setGravity(Gravity.TOP or Gravity.RIGHT) //dialog 위치 변경

		miniDialog.findViewById<TextView>(R.id.dialog_daybook_mini_btn_edit).setOnClickListener {
			// 수정
			// 원래 일기의 내용을 같이 넘겨줘야할 것 같다.
			// recordId 넘겨줘야 함
			val intent=Intent(this,DaybookWritingActivity::class.java)
			intent.putExtra("screen_type","update")

			var itemSend=DaybookToWriting(
				recordId = recordId,
				diaryTitle = binding.daybookDiaryTitle.text.toString(),
				date = binding.daybookWriteTime.text.toString(),
				title=binding.daybookTitle.text.toString(),
				content = binding.daybookContent.text.toString(),
				imgUrl = daybookImageUrl)

			intent.putExtra("item",itemSend)
//			startActivityForResult(intent,10)
			startActivity(intent)
			//finish()
			miniDialog.dismiss()

		}

		miniDialog.findViewById<TextView>(R.id.dialog_daybook_mini_btn_delete).setOnClickListener {
			// 삭제
			statusCode = 1011

			val patchDaybookRequest=PatchDaybookRequest(recordId = recordId)

			request=patchDaybookRequest
			showLoadingDialog(this)
			DaybookService(this).tryDeleteDaybook(patchDaybookRequest)
			miniDialog.dismiss()
		}

		miniDialog.show()
	}

	private fun imageDialogFunction(){
		val imgDialog = Dialog(this)
		imgDialog.setContentView(R.layout.dialog_image)

		//이미지 세팅하기
		if(!daybookImageUrl.isNullOrEmpty()){
			Glide.with(this).load(daybookImageUrl)
				.into(imgDialog.findViewById(R.id.big_image))
		}

		imgDialog.findViewById<ImageView>(R.id.big_image_close).setOnClickListener {
			imgDialog.dismiss()
		}

		imgDialog.show()
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
						getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
				}
			}
		}
		return super.dispatchTouchEvent(event)
	}

	private fun commentDialogFunction() {
		val commentDialog = Dialog(this)
		commentDialog.setContentView(R.layout.dialog_custom2)
		commentDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		commentDialog.findViewById<TextView>(R.id.dialog2_title).text = "내용을 입력해주세요."
		commentDialog.findViewById<TextView>(R.id.dialog2_btn_ok).setOnClickListener {
			commentDialog.dismiss()
		}

		commentDialog.show()
	}

	override fun onPostCommentSuccess(response: PostCommentResponse) {
		dismissLoadingDialog()
		userComment.content=binding.daybookWriteComment.text.toString()
		userComment.createdAt=sdfMini.format(System.currentTimeMillis())
		commentList.add(0,userComment)
		commentAdapter.notifyItemInserted(0)

		cmtNum+=1
		binding.daybookCommentNumber.text=(cmtNum).toString()

		binding.daybookWriteComment.setText("")
		binding.daybookScrollView.scrollTo(0,binding.daybookScrollLine.bottom) //스크롤을 밑으로
	}

	override fun onPostCommentFailure(message: String) {
		dismissLoadingDialog()
		if(message == "refreshToken") {
			val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "").toString()
			SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
		}
		// 토큰 갱신 문제가 아닐 경우
		else {
			Log.d("실패",message)
			Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
			finish() //다시 일기리스트로 돌아감
		}
	}

	override fun onGetCommentsSuccess(response: GetCommentsResponse) {
		commentList=response.information.data
		commentAdapter = CommentAdapter(commentList)
		binding.daybookCommentRV.adapter=commentAdapter
	}

	override fun onGetCommentsFailure(message: String) {
		if(message == "refreshToken") {
			val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "").toString()
			SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
		}
		// 토큰 갱신 문제가 아닐 경우
		else {
			Log.d("실패",message)
			Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
		}
	}

	override fun onPostSignOutSuccess(response: PostSignOutResponse) {}

	override fun onPostSignOutFailure(message: String) {}

	override fun onGetUsersSuccess(response: GetUsersResponse) {
		userComment= Comment(response.information.nickname,response.information.imageUrl,"","")
		if(response.information.imageUrl.isNullOrEmpty()){
			binding.daybookWriterIcon.setImageResource(R.drawable.icn_person)
		}else {
			Glide.with(this).load(response.information.imageUrl)
				.into(binding.daybookWriterIcon)
		}
	}

	override fun onGetUsersFailure(message: String) {
		dismissLoadingDialog()
		if(message == "refreshToken") {
			val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "").toString()
			SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
		}
		// 토큰 갱신 문제가 아닐 경우
		else {
			Log.d("실패",message)
			Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
		}
	}

	override fun onDeleteDaybookSuccess(response: PatchDaybookResponse) {
		dismissLoadingDialog()
		finish()
	}

	override fun onDeleteDaybookFailure(message: String) {
		dismissLoadingDialog()
		if(message == "refreshToken") {
			val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "").toString()
			SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
		}
		// 토큰 갱신 문제가 아닐 경우
		else {
			Log.d("실패",message)
			Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
		}
	}

	override fun onGetDaybookSuccess(response: GetDaybookResponse) {
		val item=response.information

		recordId=item.id
		binding.daybookDiaryTitle.text=item.diary

		if(item.date.contains("T")){
			val date= sdfFull.parse(item.date.split("T")[0])
			binding.daybookWriteTime.text= sdfFull2.format(date)
		}else if(item.date.contains("-")){ //저장시에 2023-02-13으로 했을 경우
			val date=sdfFull.parse(item.date)
			binding.daybookWriteTime.text=sdfFull2.format(date) //일단 보여주는건 2023.02.13. (월)
		}else{
			binding.daybookWriteTime.text=item.date
		}

		binding.daybookTitle.text=item.title
		binding.daybookContent.text = item.content
		binding.daybookWriter.text = item.user
		binding.daybookHeartNumber.text=item.likeCnt.toString()
		binding.daybookCommentNumber.text=item.cmtCnt.toString()
		cmtNum=item.cmtCnt

		if(item.imgUrl != null){
			daybookImageUrl=item.imgUrl
			Glide.with(this).load(item.imgUrl)
				.into(binding.daybookImage)
		}
	}

	override fun onGetDaybookFailure(message: String) {
		if(message == "refreshToken") {
			val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "").toString()
			SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
		}
		// 토큰 갱신 문제가 아닐 경우
		else {
			Log.d("실패",message)
			Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
			dismissLoadingDialog()
			finish() //다시 일기리스트로 돌아감
		}
	}

	override fun onPostRefreshSuccess(response: TokenResponse) {
		dismissLoadingDialog()
		val editor = ApplicationClass.sSharedPreferences.edit()
		editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
		editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
		editor.apply()

		when(statusCode) {
			1003 -> DaybookService(this).tryGetDaybook(recordId)
			1005 -> CommentService(this).tryGetComments(recordId)
			1007 -> MyPageService(this).tryGetUsers()
			1009 -> CommentService(this).tryPostComment(request as PostCommentRequest)
			1011 -> DaybookService(this).tryDeleteDaybook(request as PatchDaybookRequest)
			2000  -> LikesService(this).tryCheckLikes(recordId = recordId.toString())
		}
	}

	// refreshToken 갱신 실패로 로그인으로 이동
    override fun onPostRefreshFailure(message: String) {
        dismissLoadingDialog()
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.remove(ApplicationClass.X_ACCESS_TOKEN)
        editor.remove(ApplicationClass.X_REFRESH_TOKEN)
        editor.apply()

        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

	override fun onPostLikesSuccess(response: LikesResponse) {
		dismissLoadingDialog()
		binding.daybookClickHeartIcon.isSelected = true
		binding.daybookHeartNumber.text = (binding.daybookHeartNumber.text.toString().toInt() + 1).toString()
	}

	override fun onPostLikesFailure(message: String) {
		dismissLoadingDialog()
		showCustomToast("좋아요를 누를 수 없습니다.")
	}

	override fun onDeleteLikesSuccess(response: LikesResponse) {
		dismissLoadingDialog()
		binding.daybookClickHeartIcon.isSelected = false
		binding.daybookHeartNumber.text = (binding.daybookHeartNumber.text.toString().toInt() - 1).toString()
	}

	override fun onDeleteLikesFailure(message: String) {
		dismissLoadingDialog()
		showCustomToast("좋아요를 취소할 수 없습니다.")
	}

	override fun onCheckLikesSuccess(response: CheckLikesResponse) {
		dismissLoadingDialog()
		binding.daybookClickHeartIcon.isSelected = response.information.likeClicked
	}

	override fun onCheckLikesFailure(message: String) {
		dismissLoadingDialog()
		showCustomToast("좋아요를 누른 상태를 확인할 수 없습니다.")
	}
}