package com.recordOfMemory.src.main.home

import android.annotation.SuppressLint
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
import com.recordOfMemory.src.main.home.Diary.*

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var flag = 0
        fun switchFragment() {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.diary_fr, DiaryEmptyFragment())
            when(flag){
                0 -> {
                    transaction.add(R.id.diary_fr, DiaryEmptyFragment())
                    //flag = 1
                }
                1 -> {
                    transaction.replace(R.id.diary_fr, DiaryAloneFragment())
                    //flag = 2
                }
                2 -> {
                    transaction.replace(R.id.diary_fr, DiaryTogetherFragment())
                    //flag = 1
                }
            }
            transaction.addToBackStack(null)
            transaction.commit()
        }


        binding.diaryBtnAlone.setSelected(true);
        switchFragment();
        binding.diaryBtnAlone.setOnClickListener {
            flag=1;
            switchFragment();
            binding.diaryBtnAlone.setSelected(true);
            binding.diaryBtnTogether.setSelected(false);
        }

        binding.diaryBtnTogether.setOnClickListener {
            flag=2;
            switchFragment();
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