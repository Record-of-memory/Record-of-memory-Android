package com.recordOfMemory.src.main.home.diary.retrofit

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.home.diary.DiaryFragmentInterface
import com.recordOfMemory.src.main.home.diary.DiaryRetrofitInterface
import com.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryService(val diaryFragmentInterface: DiaryFragmentInterface) {
    val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"

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
        diaryRetrofitInterface.postDiaries(Authorization = X_ACCESS_TOKEN, params = params).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                (response.body() as BaseResponse?)?.let {
                    diaryFragmentInterface.onPostDiariesSuccess(
                        it
                    )
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                diaryFragmentInterface.onPostDiariesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetUsers(message: String){
        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitInterface.getUsers(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<GetUsersResponse>{
            override fun onResponse(call: Call<GetUsersResponse>, response: Response<GetUsersResponse>) {                Log.e("LOG", message)
                Log.e("LOG", message)
                if(response.code()==200){
                    diaryFragmentInterface.onGetUsersSuccess(response.body() as GetUsersResponse)
                }else{
                    diaryFragmentInterface.onGetUsersFailure("fail")
                }
            }

            override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
                diaryFragmentInterface.onGetUsersFailure(t.message ?: "통신 오류")
            }
        })
    }

}