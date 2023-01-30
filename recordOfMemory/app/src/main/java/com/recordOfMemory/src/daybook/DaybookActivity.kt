package com.recordOfMemory.src.daybook

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
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
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response

class DaybookActivity : BaseActivity<ActivityDaybookBinding>(ActivityDaybookBinding::inflate) {
	private var commentList = ArrayList<CommentData>()
	lateinit var item : GetDiary2Response

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

		Glide.with(this).load(item.imgUrl)
			.into(binding.daybookImage)

		binding.daybookIvBack.setOnClickListener {
			finish()
		}

		binding.daybookIvMore.setOnClickListener { //케밥메뉴 클릭
			miniDialogFunction()
		}

		binding.daybookImage.setOnClickListener { //이미지 클릭
			imageDialogFunction()
		}

		commentList.apply {
			add(CommentData("나나","정말 예쁜 사진이네~","22.11.23"))
			add(CommentData("짱구","노는게 제일 좋아. 친구들 모여라","22.11.22"))
			add(CommentData("뽀로로","맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래. 맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래","22.11.23"))
			add(CommentData("따라쟁이","맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래. 맞아 앞으로 댓글 많이 쓸게! 근데 쓸 말이 없으면 귀찮의니까 안 쓸래","22.11.23"))
			add(CommentData("둘리","호잇~!","22.11.20"))
			add(CommentData("뽀삐","나도 곰인형 갖고 싶어","22.11.04"))
		}

		val commentAdapter = CommentAdapter(commentList)
		binding.daybookCommentRV.adapter=commentAdapter

	}

	private fun miniDialogFunction(){
		val miniDialog = Dialog(this)
		miniDialog.setContentView(R.layout.dialog_daybook_mini)
		miniDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		miniDialog.window?.setGravity(Gravity.TOP or Gravity.RIGHT) //dialog 위치 변경

		miniDialog.findViewById<TextView>(R.id.dialog_daybook_mini_btn_edit).setOnClickListener {
			// 수정
			// 원래 일기의 내용을 같이 넘겨줘야할 것 같다.
			startActivity(Intent(this,DaybookWritingActivity::class.java))
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

		Glide.with(this).load(item.imgUrl)
			.into(imgDialog.findViewById(R.id.big_image))
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
						getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
				}
			}
		}
		return super.dispatchTouchEvent(event)
	}
}