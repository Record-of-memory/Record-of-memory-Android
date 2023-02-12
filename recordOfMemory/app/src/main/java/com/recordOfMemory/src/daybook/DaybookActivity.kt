package com.recordOfMemory.src.daybook

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.recordOfMemory.R
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
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse
import com.recordOfMemory.src.main.myPage.retrofit.MyPageInterface
import com.recordOfMemory.src.main.myPage.retrofit.MyPageService
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import java.text.SimpleDateFormat
import java.util.*

class DaybookActivity : BaseActivity<ActivityDaybookBinding>(ActivityDaybookBinding::inflate),CommentInterface,
	MyPageInterface, DaybookInterface, LikesInterface {
	private var commentList = ArrayList<Comment>()
	private lateinit var commentAdapter :CommentAdapter
	private var imageUri:String=""
	private var daybookImageUrl=""
	private val sdfMini = SimpleDateFormat("yy.MM.dd", Locale.KOREA)
	private val sdfFull=SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
	private val sdfFull2 = SimpleDateFormat("yyyy.MM.dd. (E)", Locale.KOREA) //날짜 포맷
	private var daybookId:Int =0 // 일기리스트에서 아이디 받아오면 굳이 필요 없는 변수일수도?

	private lateinit var userComment:Comment

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		daybookId = intent.getStringExtra("daybookId").toString().toInt()

		showLoadingDialog(this)
		DaybookService(this).tryGetDaybook(daybookId) //#####여기 넣을 아이디를 일기리스트에서 넘어올 때 받아올 것
		CommentService(this).tryGetComments(daybookId) //#####여기 넣을 아이디를 일기리스트에서 넘어올 때 받아올 것
		MyPageService(this).tryGetUsers()
		LikesService(this).tryCheckLikes(recordId = daybookId.toString())
		//이제 이 부분 필요 없는 내용 아닌가????? ----- 일기리스트에서 넘어올 때,일기 아이디 받아오기
//		item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//			intent.getSerializableExtra("item", GetDiary2Response::class.java)!!
//		} else {
//			intent.getSerializableExtra("item") as GetDiary2Response
//		}
//		println(item)

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
				val postCommentRequest=PostCommentRequest(recordId = daybookId, content = commentText)
				Log.d("내용",postCommentRequest.toString())
				CommentService(this).tryPostComment(postCommentRequest)

				// 현재 기기를 사용하는 유저의 정보를 백엔드에서 가져와서 세팅
				userComment.content=commentText
				userComment.createdAt=sdfMini.format(System.currentTimeMillis())
				commentList.add(0,userComment)
				commentAdapter.notifyItemInserted(0)
			}else{
				commentDialogFunction()
			}
		}

		/* 일기작성자인지 아닌지에 따라 화면이 다르게 보임 */
		binding.daybookIvMore.setOnClickListener { //케밥메뉴 클릭
			miniDialogFunction()
		}

		binding.daybookClickHeartIcon.setOnClickListener {
			if(binding.daybookClickHeartIcon.isSelected) {
				LikesService(this).tryDeleteLikes(recordId = daybookId.toString())
			}
			else {
				LikesService(this).tryPostLikes(PostLikesRequest(recordId = daybookId.toString()))
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if(resultCode== RESULT_OK){
			daybookId= data?.getStringExtra("recordId")!!.toInt()
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
				recordId = daybookId,
				diaryTitle = binding.daybookDiaryTitle.text.toString(),
				date = binding.daybookWriteTime.text.toString(),
				title=binding.daybookTitle.text.toString(),
				content = binding.daybookContent.text.toString(),
				imgUrl = daybookImageUrl)

			intent.putExtra("item",itemSend)
			startActivityForResult(intent,10)
			miniDialog.dismiss()

		}

		miniDialog.findViewById<TextView>(R.id.dialog_daybook_mini_btn_delete).setOnClickListener {
			// 삭제
			val patchDaybookRequest=PatchDaybookRequest(recordId = daybookId)
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
		binding.daybookWriteComment.setText("")
		binding.daybookScrollView.scrollTo(0,binding.daybookScrollLine.bottom) //스크롤을 밑으로
	}

	override fun onPostCommentFailure(message: String) {
		Log.d("실패","$message")
		Toast.makeText(this,"댓글 작성 실패",Toast.LENGTH_SHORT).show()
	}

	override fun onGetCommentsSuccess(response: GetCommentsResponse) {
		commentList=response.information.data
		commentAdapter = CommentAdapter(commentList)
		binding.daybookCommentRV.adapter=commentAdapter

	}

	override fun onGetCommentsFailure(message: String) {
		Log.d("실패","$message")
		Toast.makeText(this,"댓글 로딩 실패",Toast.LENGTH_SHORT).show()
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
		Log.d("실패","$message")
		Toast.makeText(this,"유저 정보를 가져올 수 없습니다",Toast.LENGTH_SHORT).show()
	}

	override fun onDeleteDaybookSuccess(response: PatchDaybookResponse) {
		finish()
	}

	override fun onDeleteDaybookFailure(message: String) {
		Log.d("실패","$message")
		Toast.makeText(this,"일기를 삭제할 수 없습니다.",Toast.LENGTH_SHORT).show()
	}

	override fun onGetDaybookSuccess(response: GetDaybookResponse) {
		val item=response.information

		daybookId=item.id
		binding.daybookDiaryTitle.text=item.diary

		if(item.date.contains("T")){
			val date= sdfFull.parse(item.date.split("T")[0])
			binding.daybookWriteTime.text= sdfFull2.format(date)
		}else{
			binding.daybookWriteTime.text=item.date
		}

		binding.daybookTitle.text=item.title
		binding.daybookContent.text = item.content
		binding.daybookWriter.text = item.user
		binding.daybookHeartNumber.text=item.likeCnt.toString()
		binding.daybookCommentNumber.text=item.cmtCnt.toString()

		if(!item.imgUrl.isNullOrEmpty()){
			daybookImageUrl=item.imgUrl
			Glide.with(this).load(item.imgUrl)
				.into(binding.daybookImage)
		}
	}

	override fun onGetDaybookFailure(message: String) {
		dismissLoadingDialog()
		Log.d("실패","$message")
		Toast.makeText(this,"일기를 가져올 수 없습니다.",Toast.LENGTH_SHORT).show()
		finish() //다시 일기리스트로 돌아감
	}

	override fun onPostLikesSuccess(response: LikesResponse) {
		binding.daybookClickHeartIcon.isSelected = true
		binding.daybookHeartNumber.text = (binding.daybookHeartNumber.text.toString().toInt() + 1).toString()
	}

	override fun onPostLikesFailure(message: String) {
		showCustomToast("좋아요를 누를 수 없습니다.")
	}

	override fun onDeleteLikesSuccess(response: LikesResponse) {
		binding.daybookClickHeartIcon.isSelected = false
		binding.daybookHeartNumber.text = (binding.daybookHeartNumber.text.toString().toInt() - 1).toString()
	}

	override fun onDeleteLikesFailure(message: String) {
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