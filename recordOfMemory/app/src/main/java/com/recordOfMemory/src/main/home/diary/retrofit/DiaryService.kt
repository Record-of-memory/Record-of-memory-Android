package com.recordOfMemory.src.main.home.diary.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.home.diary.DiaryFragmentInterface
import com.recordOfMemory.src.main.home.diary.DiaryRetrofitInterface
import com.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.config.ErrorResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DiaryService(val diaryFragmentInterface: DiaryFragmentInterface) {
    val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"

    fun  tryGetDiaries() {
        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitInterface.getDiaries(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<GetDiariesResponse>{
            override fun onResponse(call: Call<GetDiariesResponse>, response: Response<GetDiariesResponse>) {
                if(response.code() == 200) {
                    diaryFragmentInterface.onGetDiariesSuccess(response.body() as GetDiariesResponse)
                }
                else if(response.code() == 401) {
                    diaryFragmentInterface.onGetDiariesFailure("refreshToken")
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
                if(response.code() == 200) {
                    diaryFragmentInterface.onPostDiariesSuccess(response.body() as BaseResponse)
                }
                else if(response.code() == 401) {
                    diaryFragmentInterface.onPostDiariesFailure("refreshToken")
                }
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                diaryFragmentInterface.onPostDiariesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetUsers(){
        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitInterface.getUsers(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<GetUsersResponse>{
            override fun onResponse(call: Call<GetUsersResponse>, response: Response<GetUsersResponse>) {
                if(response.code()==200){
                    diaryFragmentInterface.onGetUsersSuccess(response.body() as GetUsersResponse)
//                }else{
//                    diaryFragmentInterface.onGetUsersFailure("fail")
                } else if(response.code()==401) {
                    diaryFragmentInterface.onGetUsersFailure("refreshToken")
                }
                else {
                    // error body 가져오는 코드 필요함
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            ErrorResponse::class.java)
                        // 로그인 실패 에러 메시지
                        diaryFragmentInterface.onGetUsersFailure(error.information.message)
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        diaryFragmentInterface.onGetUsersFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
                diaryFragmentInterface.onGetUsersFailure(t.message ?: "통신 오류")
            }
        })
    }

}