package com.recordOfMemory.src.main.home.diary

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
import com.recordOfMemory.databinding.FragmentDiaryAloneBinding

class DiaryAloneFragment : BaseFragment<FragmentDiaryAloneBinding>(FragmentDiaryAloneBinding::bind, R.layout.fragment_diary_alone) {
    lateinit var diaryAdapter: DiaryAdapter
    val datas = mutableListOf<DiaryData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryAdapter = DiaryAdapter(datas as ArrayList<DiaryData>)
        binding.diaryRv.adapter = diaryAdapter


        datas.apply {
            add(DiaryData(title = "나의 첫 다이어리"))
            add(DiaryData(title = "비밀일기"))
            add(DiaryData(title = "보라돌이와 함께"))
            add(DiaryData(title = "나의 첫 다이어리"))
            add(DiaryData(title = "비밀일기"))
            add(DiaryData(title = "보라돌이와 함께"))
            add(DiaryData(title = "나의 첫 다이어리"))
            add(DiaryData(title = "비밀일기"))
            add(DiaryData(title = "보라돌이와 함께"))
        }

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        diaryAdapter.datas = datas
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
            mDialogView.setCancelable(false)

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


