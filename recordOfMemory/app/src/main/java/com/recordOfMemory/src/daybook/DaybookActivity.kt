package com.recordOfMemory.src.daybook

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
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
import com.recordOfMemory.databinding.ActivityDaybookBinding
import com.recordOfMemory.src.daybook.retrofit.CommentInterface
import com.recordOfMemory.src.daybook.retrofit.CommentService
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentRequest
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response
import java.text.SimpleDateFormat
import java.util.*


class DaybookActivity : BaseActivity<ActivityDaybookBinding>(ActivityDaybookBinding::inflate),CommentInterface {
	private var commentList = ArrayList<CommentData>()
	lateinit var item : GetDiary2Response
	private val sdfMini = SimpleDateFormat("yy.MM.dd", Locale.KOREA) //날짜 포맷
	private var imageUri:String=""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			intent.getSerializableExtra("item", GetDiary2Response::class.java)!!
		} else {
			intent.getSerializableExtra("item") as GetDiary2Response
		}
		println(item)
		binding.daybookWriteTime.text = item.date
		binding.daybookTitle.text = item.title
		binding.daybookContent.text = item.content
		binding.daybookWriter.text = item.writer

		if(!item.imgUrl.isNullOrEmpty()){
			Glide.with(this).load(item.imgUrl)
				.into(binding.daybookImage)
		}

		imageUri= intent.getStringExtra("imageUri").toString()
		if(!imageUri.isNullOrEmpty()){
			val img= Uri.parse(imageUri)
			binding.daybookImage.setImageURI(img)
		}


		binding.daybookIvBack.setOnClickListener {
			finish()
		}


		binding.daybookImage.setOnClickListener { //이미지 클릭
			imageDialogFunction()
		}

		if(intent.getStringExtra("screen_type")=="read"){ // 더미 데이터를 위한 댓글
			commentList.apply {
				add(CommentData("나나","정말 예쁜 사진이네~","22.11.23"))
				add(CommentData("짱구","노는게 제일 좋아. 친구들 모여라","22.11.22"))
				add(CommentData("뽀로로","맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래. 맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래","22.11.23"))
				add(CommentData("따라쟁이","맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래. 맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래","22.11.23"))
				add(CommentData("둘리","호잇~!","22.11.20"))
				add(CommentData("뽀삐","나도 곰인형 갖고 싶어","22.11.04"))
			}
		}

		val commentAdapter = CommentAdapter(commentList)
		binding.daybookCommentRV.adapter=commentAdapter


		binding.daybookBtnSubmit.setOnClickListener {
			var comment=binding.daybookWriteComment
			if(!comment.text.toString().isNullOrEmpty()){
				//댓글 업데이트 - 사용자의 이름을 알고있어야 함
				//백엔드에 정보 보내기

				//일기 정보 불러와서 id 수정
				val postCommentRequest=PostCommentRequest( recordId = 3, content = comment.text.toString())
				Log.d("내용",postCommentRequest.toString())
				CommentService(this).tryPostComment(postCommentRequest)

				//이부분 댓글 조회 이후 수정
				commentList.add(CommentData("kari",comment.text.toString(),sdfMini.format(System.currentTimeMillis())))
				commentAdapter.notifyDataSetChanged()

			}else{
				commentDialogFunction()
			}
		}

		/* 일기작성자인지 아닌지에 따라 화면이 다르게 보임 */
		binding.daybookIvMore.setOnClickListener { //케밥메뉴 클릭
			miniDialogFunction()
		}

		binding.daybookClickHeartIcon.setOnClickListener {
			binding.daybookClickHeartIcon.isSelected = !binding.daybookClickHeartIcon.isSelected
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

			val intent=Intent(this,DaybookWritingActivity::class.java)
			intent.putExtra("screen_type","update")

			var itemSend= GetDiary2Response(itemId = "99", title = item.title, content = item.content, date = item.date,writer = item.writer,
				imgUrl = item.imgUrl)

			intent.putExtra("item",itemSend)
			if(!imageUri.isNullOrEmpty()){
				intent.putExtra("imageUri",imageUri)
			}
			startActivity(intent)
			miniDialog.dismiss()

		}

		miniDialog.findViewById<TextView>(R.id.dialog_daybook_mini_btn_delete).setOnClickListener {
			// 삭제
			Toast.makeText(this,"일기 삭제",Toast.LENGTH_SHORT).show()
			miniDialog.dismiss()
		}

		miniDialog.show()
	}

	private fun imageDialogFunction(){
		val imgDialog = Dialog(this)
		imgDialog.setContentView(R.layout.dialog_image)

		//이미지 세팅하기
		if(!item.imgUrl.isNullOrEmpty()){
			Glide.with(this).load(item.imgUrl)
				.into(imgDialog.findViewById(R.id.big_image))
		}
		if(!imageUri.isNullOrEmpty()){
			imgDialog.findViewById<ImageView>(R.id.big_image).setImageURI(Uri.parse(imageUri))
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
}