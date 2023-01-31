package com.recordOfMemory.src.main.home.Diary

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
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
            // Dialog만들기
//            val mDialogView = LayoutInflater.from(this).inflate(R.layout.fragment_pop_diary, null)
//            val mBuilder = AlertDialog.Builder(this)
//                .setView(mDialogView)
//            val  mAlertDialog = mBuilder.show()
//            mAlertDialog.setContentView(R.layout.fragment_pop_diary)
//            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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
                Toast
                    .makeText(requireContext(), "일기_전체보기로 이동", Toast.LENGTH_SHORT)
                    .show()
            }

            //            val mDialogView = LayoutInflater.from(this).inflate(R.layout.fragment_pop_diary, null)
//            val mBuilder = AlertDialog.Builder(this)
//                .setView(mDialogView)
//            val  mAlertDialog = mBuilder.show()
//            mAlertDialog.setContentView(R.layout.fragment_pop_diary)
//            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//            //mAlertDialog.show()
//
//            val noButton = mDialogView.findViewById<Button>(R.id.pop_btn_close)
//            noButton.setOnClickListener {
//                mAlertDialog.dismiss()
//            }
//
//            val confirm = mDialogView.findViewById<Button>(R.id.pop_btn_confirm)
//            confirm.setOnClickListener() {
//                Toast
//                    .makeText(this, "일기_전체보기로 이동", Toast.LENGTH_SHORT)
//                    .show()
//            }
        }
    }
}