package com.recordOfMemory.src.main.home.Diary

import android.app.Dialog
import android.content.Intent.getIntent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass.Companion.sRetrofit
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiaryResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DiaryTogetherFragment : BaseFragment<FragmentDiaryTogetherBinding>(FragmentDiaryTogetherBinding::bind, R.layout.fragment_diary_together) {
    lateinit var diaryAdapter: DiaryAdapter
    val DiaryData = mutableListOf<DiaryData>()
    lateinit var item : GetDiaryResponse
    private var nickname:String=""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryAdapter = DiaryAdapter(DiaryData as ArrayList<DiaryData>)
        binding.diaryRv.adapter = diaryAdapter

        DiaryData.apply {
            add(DiaryData(title = "우정일기", diaryType= "WITH"))
            add(DiaryData(title = "우리끼리", diaryType= "WITH"))
            add(DiaryData(title = "여행일기", diaryType= "WITH"))
            add(DiaryData(title = "우정일기", diaryType= "WITH"))
            add(DiaryData(title = "우리끼리", diaryType= "WITH"))
            add(DiaryData(title = "여행일기", diaryType= "WITH"))
            add(DiaryData(title = "우정일기", diaryType= "WITH"))
            add(DiaryData(title = "우리끼리", diaryType= "WITH"))
            add(DiaryData(title = "여행일기", diaryType= "WITH"))
        } //더미데이터

        diaryAdapter.DiaryData = DiaryData
        diaryAdapter.notifyDataSetChanged()

        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager

        binding.diaryBtnTogether.isSelected = true

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        //백엔드로부터 user 정보 받아서 diary_tv_title 변경하기
        loadData()

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

    fun loadData() {
        val diaryRetrofitService = sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitService.getDiary().enqueue(object : Callback<GetDiaryResponse>{
            override fun onResponse(call: Call<GetDiaryResponse>, response: Response<GetDiaryResponse>) {
                if (response.isSuccessful) {
                   val body = response.body()
                   body?.let {
                       load(response.body())
                   }
                }
            }

            override fun onFailure(call: Call<GetDiaryResponse>, t: Throwable) {
                t.message?.let { Log.d("this is error", it) }
            }
        })
    }

    fun load(test: GetDiaryResponse?){
        DiaryData.clear()
        for (document in test!!.id) {
            DiaryData.apply{add(DiaryData(title=test.name, diaryType = test.diaryType))}
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
            val newItem = mDialogView.findViewById<EditText>(R.id.pop_et_name).text.toString()
            if (newItem.length == 0) {
                Toast
                    .makeText(requireContext(), "다이어리 제목을 한 글자 이상 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var contact = DiaryData(title = newItem, diaryType= "WITH")
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