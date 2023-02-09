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
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.ApplicationClass.Companion.sRetrofit
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DiaryTogetherFragment : BaseFragment<FragmentDiaryTogetherBinding>(FragmentDiaryTogetherBinding::bind, R.layout.fragment_diary_together), DiaryFragmentInterface  {
    lateinit var diaryAdapter: DiaryAdapter
    //val DiaryData = mutableListOf<ResultDiaries>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val editor = ApplicationClass.sSharedPreferences.edit()
//        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.accssToken)
//        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.accssToken)
//        editor.apply()
//
//        val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN)


//        diaryAdapter = DiaryAdapter(DiaryData as ArrayList<ResultDiaries>)
//        binding.diaryRv.adapter = diaryAdapter
//        DiaryData.apply {
//            add(ResultDiaries(id=0,nickname="",name = "우정일기", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "우리끼리", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "여행일기", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "우정일기", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "우리끼리", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "여행일기", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "우정일기", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "우리끼리", diaryType= "WITH"))
//            add(ResultDiaries(id=0,nickname="",name = "여행일기", diaryType= "WITH"))
//        } //더미데이터
//        diaryAdapter.DiaryData = DiaryData
//        diaryAdapter.notifyDataSetChanged()
//        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
//        binding.diaryRv.layoutManager = manager

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
                //DiaryData.add(newItem)
                diaryAdapter.notifyDataSetChanged()
                DiaryService(this).tryPostDiaries(newItem)
                mDialogView.dismiss()
            }
            //val postDiariesRequest = PostDiariesRequest(name = newItemName, diaryType = "WITH")

            }
        }

    override fun onGetDiariesSuccess(response: GetDiariesResponse) {
        val data = response.result
        if (response.isSuccess) {
            diaryAdapter = DiaryAdapter(data)
            binding.diaryRv.adapter = diaryAdapter
            diaryAdapter.notifyDataSetChanged()
            val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            binding.diaryRv.layoutManager = manager


//            val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
//            val diaryAdapter = DiaryAdapter(data)
//            binding.diaryRv.apply {
//                layoutManager=manager
//                adapter=diaryAdapter
//            }
            //else { // 네트워크 실패 - 응답은 성공, 바디에 내용은 없는 경우
                // <아직 다이어리가 없어요. 첫 다이어리를 만들어보세요!> 프레그먼트 띄우기
            //}
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

}


//    fun loadData() {
//        val diaryRetrofitService = sRetrofit.create(DiaryRetrofitInterface::class.java)
//        diaryRetrofitService.getDiary().enqueue(object : Callback<GetDiaryResponse>{
//            override fun onResponse(call: Call<GetDiaryResponse>, response: Response<GetDiaryResponse>) {
//                if (response.isSuccessful) {
//                    val body = response.body()
//                    if (response.code()==200) {
//                        load(body) //이 단계에서 diaryType="WITH"을 필터링할지 load 내에서 필터링할지
//                    } else { // 네트워크 실패 - 응답은 성공, 바디에 내용은 없는 경우
//                    // <아직 다이어리가 없어요. 첫 다이어리를 만들어보세요!> 프레그먼트 띄우기
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<GetDiaryResponse>, t: Throwable) {
//                t.message?.let { Log.d("this is error", it) }
//            }
//        })
//    }
//
//    fun load(body: GetDiaryResponse?){ //데이터 받아오는거 수정하기...
//        DiaryData.clear()
//        for (document in body!!.id) {
//            DiaryData.apply{add(DiaryData(title=body.name, diaryType = body.diaryType))}
//        }
//    }