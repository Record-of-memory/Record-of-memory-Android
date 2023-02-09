package com.recordOfMemory.src.main.home.Diary

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse

class DiaryTogetherFragment : BaseFragment<FragmentDiaryTogetherBinding>(FragmentDiaryTogetherBinding::bind, R.layout.fragment_diary_together), DiaryFragmentInterface  {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.diaryBtnTogether.isSelected = true

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        DiaryService(this).tryGetDiaries()

        binding.diaryBtnAlone.setOnClickListener { //혼자쓰는 으로 전환
            transaction
                .replace(R.id.main_frm, DiaryAloneFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.iconDiaryAdd.setOnClickListener { //새 다이어리 추가 버튼 누를시
            addNewDiaryFunction()
        }
    }

    private fun addNewDiaryFunction() {
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
            val newItemName = mDialogView.findViewById<EditText>(R.id.pop_et_name).text.toString()
            if (0 < newItemName.length && newItemName.length < 10){
                var newItem = PostDiariesRequest(name = newItemName, diaryType= "WITH")
                DiaryService(this).tryPostDiaries(newItem)
                mDialogView.dismiss()
            }
            DiaryService(this).tryGetDiaries()
        }
    }

    override fun onGetDiariesSuccess(response: GetDiariesResponse) {
//        val data = response.information
//        val diaryAdapter = DiaryAdapter(data)
//        binding.diaryRv.adapter = diaryAdapter
//        diaryAdapter.notifyDataSetChanged()
//        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
//        binding.diaryRv.layoutManager = manager
    }

    override fun onGetDiariesFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostDiariesSuccess(response: PostDiariesResponse) {
        showCustomToast("성공")
    }

    override fun onPostDiariesFailure(message: String) {
        showCustomToast("오류 : $message")
    }

}