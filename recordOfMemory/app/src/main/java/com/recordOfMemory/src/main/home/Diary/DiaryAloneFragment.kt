package com.recordOfMemory.src.main.home.Diary

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiaryAloneBinding
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryAloneFragment : BaseFragment<FragmentDiaryAloneBinding>(FragmentDiaryAloneBinding::bind, R.layout.fragment_diary_alone), DiaryFragmentInterface {
    lateinit var diaryAdapter: DiaryAdapter
    val DiaryData = mutableListOf<ResultDiaries>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryAdapter = DiaryAdapter(DiaryData as ArrayList<ResultDiaries>)
        binding.diaryRv.adapter = diaryAdapter


        DiaryData.apply {
            add(ResultDiaries(id=0,nickname="",name = "나의 첫 다이어리", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "비밀일기", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "보라돌이와 함께", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "나의 첫 다이어리", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "비밀일기", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "보라돌이와 함께", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "나의 첫 다이어리", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "비밀일기", diaryType= "ALONE"))
            add(ResultDiaries(id=0,nickname="",name = "보라돌이와 함께", diaryType= "ALONE"))
        }

        diaryAdapter.DiaryData = DiaryData
        diaryAdapter.notifyDataSetChanged()

        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager

        binding.diaryBtnAlone.isSelected = true

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        //백엔드로부터 user 정보 받아서 diary_tv_title 변경하기
        //load() //유저에 해당하는 다이어리 데이터
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


//    fun load(){ //데이터 받아오는거 수정하기...
//        DiaryService(this).tryGetDiaries()
//        for (document in body!!.id) {
//            DiaryData.apply{add(PostDiariesRequest(name=body.name, diaryType = body.diaryType))}
//        }
//    }

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
                //DiaryData.add(newItem)
                diaryAdapter.notifyDataSetChanged()
                DiaryService(this).tryPostDiaries(newItem)
                mDialogView.dismiss()
            }
                //벡앤드에 데이터 전달
//                val diaryRetrofitService = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
//                diaryRetrofitService.postDiary(newItemName,"WITH").enqueue(object : Callback<GetDiaryResponse>{
//                    override fun onResponse(call: Call<GetDiaryResponse>, response: Response<GetDiaryResponse>) {
//                        if (response.isSuccessful) {
//                            var data = response.body()
//                        }
//                    }
//                    override fun onFailure(call: Call<GetDiaryResponse>, t: Throwable) {
//                        t.message?.let { Log.d("this is error", it) }
//                    }
//                })

        }
    }

    override fun onGetDiariesSuccess(response: GetDiariesResponse) {
        if (response.isSuccess) {
            val body = response.result
            if (response.code == 200) {
                body?.let {
                    //어댑터로 내용 뿌리기?
//                    for (document in body!!.id) {
//                        DiaryData.apply {
//                            add(
//                                ResultDiaries(
//                                    name = body.name,
//                                    diaryType = body.diaryType
//                                )
//                            )}
//                        }
                    }
                } else { // 네트워크 실패 - 응답은 성공, 바디에 내용은 없는 경우
                    // <아직 다이어리가 없어요. 첫 다이어리를 만들어보세요!> 프레그먼트 띄우기
            }
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


