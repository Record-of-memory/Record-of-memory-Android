package com.recordOfMemory.src.main.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.databinding.ActivityDiaryBinding
import com.recordOfMemory.src.main.home.Diary.DiaryAdapter
import com.recordOfMemory.src.main.home.Diary.DiaryData

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val itemList = ArrayList<DiaryData>()

        itemList.add(DiaryData("나의 첫 다이어리"))
        itemList.add(DiaryData("비밀일기"))
        itemList.add(DiaryData("보라돌이와 함께"))
        itemList.add(DiaryData("나의 첫 다이어리"))
        itemList.add(DiaryData("비밀일기"))
        itemList.add(DiaryData("보라돌이와 함께"))
        itemList.add(DiaryData("나의 첫 다이어리"))
        itemList.add(DiaryData("비밀일기"))
        itemList.add(DiaryData("보라돌이와 함께"))

        val DiaryAdapter = DiaryAdapter(itemList)
        DiaryAdapter.notifyDataSetChanged()

        binding.diaryRv.adapter = DiaryAdapter
        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.setLayoutManager(gridLayoutManager)

        binding.diaryBtnAlone.setSelected(true);
        binding.diaryBtnAlone.setOnClickListener {
            binding.diaryBtnAlone.setSelected(true);
            binding.diaryBtnTogether.setSelected(false);
        }
        binding.diaryBtnTogether.setOnClickListener {
            binding.diaryBtnTogether.setSelected(true);
            binding.diaryBtnAlone.setSelected(false);
        }

        binding.iconDiaryAdd.setOnClickListener {
            // Dialog만들기
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.fragment_pop_diary, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val  mAlertDialog = mBuilder.show()
            val noButton = mDialogView.findViewById<Button>(R.id.pop_btn_close)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
            val confirm = mDialogView.findViewById<Button>(R.id.pop_btn_confirm)
            confirm.setOnClickListener() {
                Toast
                    .makeText(this, "일기_전체보기로 이동", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}