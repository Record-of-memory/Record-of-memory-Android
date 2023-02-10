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
import com.recordOfMemory.databinding.FragmentDiaryAloneBinding
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse

class DiaryAloneFragment : BaseFragment<FragmentDiaryAloneBinding>(FragmentDiaryAloneBinding::bind, R.layout.fragment_diary_alone), DiaryFragmentInterface {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.diaryBtnAlone.isSelected = true

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        DiaryService(this).tryGetDiaries()

        binding.diaryBtnTogether.setOnClickListener { //같이쓰는 으로 전환
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
            val newItemName = mDialogView.findViewById<EditText>(R.id.pop_et_name).text.toString()
            if (0 < newItemName.length && newItemName.length < 10){ //10자 이상하면 버튼이 회색으로 변하게하기
                var newItem = PostDiariesRequest(name = newItemName, diaryType= "ALONE")
                DiaryService(this).tryPostDiaries(newItem)
                mDialogView.dismiss()
            }
            DiaryService(this).tryGetDiaries()
        }
    }

    override fun onGetDiariesSuccess(response: GetDiariesResponse) {
        val data = response.information
        val userName = data[0].nickname
        binding.diaryTvTitle.text=userName+"님의 기억을 기록할 다이어리를 골라보세요!"
        val filterData = data.filter { it.diaryType=="ALONE" }
        val diaryAdapter = DiaryAdapter(filterData as ArrayList<ResultDiaries>)
        binding.diaryRv.adapter = diaryAdapter
        diaryAdapter.notifyDataSetChanged()
        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager
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


