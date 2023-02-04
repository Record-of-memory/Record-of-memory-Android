package com.recordOfMemory.src.main.home.Diary

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiaryAloneBinding

class DiaryAloneFragment : BaseFragment<FragmentDiaryAloneBinding>(FragmentDiaryAloneBinding::bind, R.layout.fragment_diary_alone) {
    lateinit var diaryAdapter: DiaryAdapter
    val DiaryData = mutableListOf<DiaryData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryAdapter = DiaryAdapter(DiaryData as ArrayList<DiaryData>)
        binding.diaryRv.adapter = diaryAdapter


        DiaryData.apply {
            add(DiaryData(title = "나의 첫 다이어리", diaryType= "ALONE"))
            add(DiaryData(title = "비밀일기", diaryType= "ALONE"))
            add(DiaryData(title = "보라돌이와 함께", diaryType= "ALONE"))
            add(DiaryData(title = "나의 첫 다이어리", diaryType= "ALONE"))
            add(DiaryData(title = "비밀일기", diaryType= "ALONE"))
            add(DiaryData(title = "보라돌이와 함께", diaryType= "ALONE"))
            add(DiaryData(title = "나의 첫 다이어리", diaryType= "ALONE"))
            add(DiaryData(title = "비밀일기", diaryType= "ALONE"))
            add(DiaryData(title = "보라돌이와 함께", diaryType= "ALONE"))
        }

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        diaryAdapter.DiaryData = DiaryData
        diaryAdapter.notifyDataSetChanged()

        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager

        binding.diaryBtnAlone.isSelected = true
        binding.diaryBtnTogether.setOnClickListener {
            transaction
                .replace(R.id.main_frm, DiaryTogetherFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.iconDiaryAdd.setOnClickListener {
            addNewDiaryFunction()
        }
    }
    private fun addNewDiaryFunction(){
        val mDialogView = Dialog(requireContext())
        mDialogView.setContentView(R.layout.fragment_pop_diary)
        mDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialogView.show()

        val noButton = mDialogView.findViewById<Button>(R.id.pop_btn_close)
        noButton.setOnClickListener {
            mDialogView.dismiss()
        }

        val confirm = mDialogView.findViewById<Button>(R.id.pop_btn_confirm)
        confirm.setOnClickListener() {
            val newItem = mDialogView.findViewById<EditText>(R.id.pop_et_name).text.toString()
            if (newItem.length == 0) {
                Toast
                    .makeText(requireContext(), "다이어리 제목을 한 글자 이상 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var contact = DiaryData(title = newItem, diaryType= "ALONE")
                DiaryData.add(contact)
                diaryAdapter.notifyDataSetChanged()
                mDialogView.dismiss() //다이어리 생성 후에도 모달창 사라짐
                Toast
                    .makeText(requireContext(), "새 다이어리가 생성되었습니다", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}


