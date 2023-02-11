//package com.recordOfMemory.src.main.home.diary
//
//import com.recordOfMemory.config.ApplicationClass
//import com.recordOfMemory.config.BaseResponse
//import com.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
//import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
//import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
//
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class DiaryService(val diaryFragmentInterface: DiaryFragmentInterface) {
//    val X_ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNjc2MDM4MTYxLCJleHAiOjE2NzYwNDE3NjF9.1Uu0p_BL7T8jheaFzWf_3-P9JQdiSFZVYjJfeFS4-YiDTfYcmQ0e-rhvnJ3sfhdd18NUN25ZVHswSEGBGqtYBA"
//    //val X_ACCESS_TOKEN = "Bearer " + ApplicationClass.X_ACCESS_TOKEN
//
//    fun  tryGetDiaries() {
//        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
//        diaryRetrofitInterface.getDiaries(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<GetDiariesResponse>{
//            override fun onResponse(call: Call<GetDiariesResponse>, response: Response<GetDiariesResponse>) {
//                (response.body() as GetDiariesResponse?)?.let {
//                    diaryFragmentInterface.onGetDiariesSuccess(
//                        it
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<GetDiariesResponse>, t: Throwable) {
//                diaryFragmentInterface.onGetDiariesFailure(t.message ?: "통신 오류")
//            }
//        })
//    }
//
//    fun tryPostDiaries(params: PostDiariesRequest){
//        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
//        diaryRetrofitInterface.postDiaries(Authorization = X_ACCESS_TOKEN, params = params).enqueue(object : Callback<BaseResponse>{
//            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
//                (response.body() as BaseResponse?)?.let {
//                    diaryFragmentInterface.onPostDiariesSuccess(
//                        it
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
//                diaryFragmentInterface.onPostDiariesFailure(t.message ?: "통신 오류")
//            }
//        })
//    }
//
//    fun tryGetUsers(){
//        val diaryRetrofitInterface = ApplicationClass.sRetrofit.create(DiaryRetrofitInterface::class.java)
//        diaryRetrofitInterface.getUsers(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<GetUsersResponse>{
//            override fun onResponse(call: Call<GetUsersResponse>, response: Response<GetUsersResponse>) {
//                if(response.code()==200){
//                    diaryFragmentInterface.onGetUsersSuccess(response.body() as GetUsersResponse)
//                }else{
//                    diaryFragmentInterface.onGetUsersFailure("fail")
//                }
//            }
//
//            override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
//                diaryFragmentInterface.onGetUsersFailure(t.message ?: "통신 오류")
//            }
//        })
//    }
//
//
//
//}
