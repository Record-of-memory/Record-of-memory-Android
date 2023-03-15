package kr.co.app.recordOfMemory.src.main.home.diary.retrofit

import com.google.gson.GsonBuilder
import kr.co.app.recordOfMemory.src.main.home.diary.DiaryFragmentInterface
import kr.co.app.recordOfMemory.src.main.home.diary.DiaryRetrofitInterface
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DiaryService(val diaryFragmentInterface: DiaryFragmentInterface) {
    val token = kr.co.app.recordOfMemory.config.ApplicationClass.sSharedPreferences.getString(
        kr.co.app.recordOfMemory.config.ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"

    fun  tryGetDiaries() {
        val diaryRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
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
        val diaryRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
        diaryRetrofitInterface.postDiaries(Authorization = X_ACCESS_TOKEN, params = params).enqueue(object : Callback<kr.co.app.recordOfMemory.config.BaseResponse>{
            override fun onResponse(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, response: Response<kr.co.app.recordOfMemory.config.BaseResponse>) {
                if(response.code() == 200) {
                    diaryFragmentInterface.onPostDiariesSuccess(response.body() as kr.co.app.recordOfMemory.config.BaseResponse)
                }
                else if(response.code() == 401) {
                    diaryFragmentInterface.onPostDiariesFailure("refreshToken")
                }
            }
            override fun onFailure(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, t: Throwable) {
                diaryFragmentInterface.onPostDiariesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetUsers(){
        val diaryRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
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
                            kr.co.app.recordOfMemory.config.ErrorResponse::class.java)
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