package com.recordOfMemory.src.main.home.diary.retrofit

import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiaryRequest
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiaryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryService(val diaryInterface: DiaryInterface) {

    fun tryPostDiaries(params: PostDiaryRequest){
        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitInterface.getDiaries(Authorization = "", params = params).enqueue(object : Callback<PostDiaryResponse> {
            override fun onResponse(call: Call<PostDiaryResponse>, response: Response<PostDiaryResponse>) {
                diaryInterface.onPostDiarySuccess(response.body() as PostDiaryResponse)
            }

            override fun onFailure(call: Call<PostDiaryResponse>, t: Throwable) {
                diaryInterface.onPostDiaryFailure(t.message ?: "통신 오류")
            }
        })
    }

}