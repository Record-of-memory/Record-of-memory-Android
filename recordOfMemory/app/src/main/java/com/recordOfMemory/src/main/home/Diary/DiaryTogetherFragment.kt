package com.recordOfMemory.src.main.home.Diary

import android.app.Dialog
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
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding

class DiaryTogetherFragment : BaseFragment<FragmentDiaryTogetherBinding>(FragmentDiaryTogetherBinding::bind, R.layout.fragment_diary_together) {
    lateinit var diaryAdapter: DiaryAdapter
    val datas = mutableListOf<DiaryData>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryAdapter = DiaryAdapter(datas as ArrayList<DiaryData>)
        binding.diaryRv.adapter = diaryAdapter

        datas.apply {
            add(DiaryData(title = "우정일기"))
            add(DiaryData(title = "우리끼리"))
            add(DiaryData(title = "여행일기"))
            add(DiaryData(title = "우정일기"))
            add(DiaryData(title = "우리끼리"))
            add(DiaryData(title = "여행일기"))
            add(DiaryData(title = "우정일기"))
            add(DiaryData(title = "우리끼리"))
            add(DiaryData(title = "여행일기"))
        }

        diaryAdapter.datas = datas
        diaryAdapter.notifyDataSetChanged()

        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager

        binding.diaryBtnTogether.isSelected = true

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        binding.diaryBtnAlone.setOnClickListener {
            transaction
                .replace(R.id.main_frm, DiaryAloneFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.iconDiaryAdd.setOnClickListener {
            val mDialogView = Dialog(requireContext())
            mDialogView.setContentView(R.layout.fragment_pop_diary)
            mDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialogView.show()
            mDialogView.setCancelable(false)

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
                    var contact = DiaryData(title = newItem)
                    datas.add(contact)
                    diaryAdapter.notifyDataSetChanged()
                    mDialogView.dismiss() //다이어리 생성 후에도 모달창 사라짐
                    Toast
                        .makeText(requireContext(), "새 다이어리가 생성되었습니다", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}