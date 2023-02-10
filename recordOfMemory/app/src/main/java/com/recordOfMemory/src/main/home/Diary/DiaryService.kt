package com.recordOfMemory.src.main.home.Diary

import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryService(val diaryFragmentInterface: DiaryFragmentInterface) {
    val X_ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNjc2MDM1MDA2LCJleHAiOjE2NzYwMzg2MDZ9.kW35dty0C_wkhtAz883F4RDQX9t4P6c2Q-86tXR2YMRzYLBO3JfGmT8u0THZe6MiFvRFsAPAxE_dtcS-KI1lvw"
    //val X_ACCESS_TOKEN = "Bearer " + ApplicationClass.X_ACCESS_TOKEN

    fun  tryGetDiaries() {
        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitInterface.getDiaries(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<GetDiariesResponse>{
            override fun onResponse(call: Call<GetDiariesResponse>, response: Response<GetDiariesResponse>) {
                (response.body() as GetDiariesResponse?)?.let {
                    diaryFragmentInterface.onGetDiariesSuccess(
                        it
                    )
                }
            }

            override fun onFailure(call: Call<GetDiariesResponse>, t: Throwable) {
                diaryFragmentInterface.onGetDiariesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostDiaries(params: PostDiariesRequest){
        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitInterface.postDiaries(Authorization = X_ACCESS_TOKEN, params = params).enqueue(object : Callback<PostDiariesResponse>{
            override fun onResponse(call: Call<PostDiariesResponse>, response: Response<PostDiariesResponse>) {
                (response.body() as PostDiariesResponse?)?.let {
                    diaryFragmentInterface.onPostDiariesSuccess(
                        it
                    )
                }
            }

            override fun onFailure(call: Call<PostDiariesResponse>, t: Throwable) {
                diaryFragmentInterface.onPostDiariesFailure(t.message ?: "통신 오류")
            }
        })
    }

}
