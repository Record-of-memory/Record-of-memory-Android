package com.recordOfMemory.src.main.home.Diary

import android.app.Dialog
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse
import com.recordOfMemory.src.main.home.Diary.DiaryEmptyFragment
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetUsersResponse

class DiaryTogetherFragment : BaseFragment<FragmentDiaryTogetherBinding>(FragmentDiaryTogetherBinding::bind, R.layout.fragment_diary_together), DiaryFragmentInterface  {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        binding.diaryBtnTogether.isSelected = true

        binding.diaryRv.visibility=View.INVISIBLE
        binding.diaryIvNone.visibility=View.INVISIBLE
        binding.diaryTvNone.visibility=View.INVISIBLE


        DiaryService(this).tryGetUsers()

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
            binding.diaryTvNone.visibility=View.INVISIBLE
            binding.diaryIvNone.visibility=View.INVISIBLE
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
                DiaryService(this).tryGetDiaries()
            } else{
                val alarm = mDialogView.findViewById<TextView>(R.id.pop_tv_alarm)
                alarm.text = "10자 이내로 설정해주세요"
            }
        }
    }


    override fun onGetDiariesSuccess(response: GetDiariesResponse) {
        val data = response.information
//        val userName = data[0].nickname
//        binding.diaryTvTitle.text=userName+"님의 기억을 기록할 다이어리를 골라보세요!"
        //binding.diaryTvTitle.text=nickname+"님의 기억을 기록할 다이어리를 골라보세요!"

        val filterData = data.filter { it.diaryType=="WITH" }
        if (!filterData.isEmpty()) {
            binding.diaryRv.visibility=View.VISIBLE
            val diaryAdapter = DiaryAdapter(filterData as ArrayList<ResultDiaries>)
            binding.diaryRv.adapter = diaryAdapter
            diaryAdapter.notifyDataSetChanged()
            val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            binding.diaryRv.layoutManager = manager
        } else{
            binding.diaryTvNone.visibility=View.VISIBLE
            binding.diaryIvNone.visibility=View.VISIBLE
        }
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

    override fun onGetUsersSuccess(response: GetUsersResponse) {
        val nickname = response.information.nickname
        binding.diaryTvTitle.text=nickname+"님의 기억을 기록할 다이어리를 골라보세요!"
    }

    override fun onGetUsersFailure(message: String) {
        showCustomToast("오류 : $message")
    }

}