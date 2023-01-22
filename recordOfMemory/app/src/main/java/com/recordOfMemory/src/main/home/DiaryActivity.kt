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
import com.recordOfMemory.src.main.home.Diary.DiaryAdapter
import com.recordOfMemory.src.main.home.Diary.DiaryData

class DiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diary_rv = findViewById<RecyclerView>(R.id.diary_rv)

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

        /*diary_rv.adapter =  DiaryAdapter
        diary_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)*/

        diary_rv.adapter = DiaryAdapter
        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        diary_rv.setLayoutManager(gridLayoutManager)

        var btn_alone: Button = findViewById<Button>(R.id.diary_btn_alone)
        var btn_together: Button = findViewById<Button>(R.id.diary_btn_together)
        var btn_add: Button = findViewById<Button>(R.id.icon_diary_add)


        btn_alone.setSelected(true);
        btn_alone.setOnClickListener {
            btn_alone.setSelected(true);
            btn_together.setSelected(false);
        }
        btn_together.setOnClickListener {
            btn_together.setSelected(true);
            btn_alone.setSelected(false);
        }
        /*btn_add.setOnClickListener() {
            Toast
                .makeText(this, "toast message", Toast.LENGTH_SHORT)
                .show()
        }*/

        /*var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)*/

        btn_add.setOnClickListener {
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