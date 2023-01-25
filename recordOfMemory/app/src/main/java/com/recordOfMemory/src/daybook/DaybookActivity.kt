package com.recordOfMemory.src.daybook

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseActivity
import com.recordOfMemory.databinding.ActivityDaybookBinding

class DaybookActivity : BaseActivity<ActivityDaybookBinding>(ActivityDaybookBinding::inflate) {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.daybookIvMore.setOnClickListener { //케밥메뉴 클릭
			miniDialogFunction()
		}

		binding.daybookImage.setOnClickListener { //이미지 클릭
			imageDialogFunction()
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

		imgDialog.findViewById<ImageView>(R.id.big_image_close).setOnClickListener {
			imgDialog.dismiss()
		}

		imgDialog.show()
	}
}