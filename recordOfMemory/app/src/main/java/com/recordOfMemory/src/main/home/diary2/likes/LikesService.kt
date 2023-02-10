package com.recordOfMemory.src.main.home.diary2.likes

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikesService(val likesInterface: LikesInterface) {
    val X_ACCESS_TOKEN =""

    fun tryPostLikes(postLikesRequest: PostLikesRequest){
        val likesRetrofitInterface = ApplicationClass.sRetrofit.create(LikesRetrofitInterface::class.java)
        likesRetrofitInterface.postlikes(Authorization = X_ACCESS_TOKEN, params = postLikesRequest).enqueue(object :
            Callback<LikesResponse> {
            override fun onResponse(call: Call<LikesResponse>, response: Response<LikesResponse>) {
                if(response.isSuccessful){
                    likesInterface.onPostLikesSuccess(response.body() as LikesResponse)
                }else{
                    Log.d("fail","fail to post likes")
                    likesInterface.onPostLikesFailure("fail")
                }
            }

            override fun onFailure(call: Call<LikesResponse>, t: Throwable) {
                likesInterface.onPostLikesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteLikes(diaryId: String){
        val likesRetrofitInterface = ApplicationClass.sRetrofit.create(LikesRetrofitInterface::class.java)
        likesRetrofitInterface.deletelikes(Authorization = X_ACCESS_TOKEN, diaryId = diaryId).enqueue(object : Callback<LikesResponse> {
            override fun onResponse(call: Call<LikesResponse>, response: Response<LikesResponse>) {
                if(response.isSuccessful){
                    likesInterface.onDeleteLikesSuccess(response.body() as LikesResponse)
                }else{
                    Log.d("fail","fail to delete likes")
                    likesInterface.onDeleteLikesFailure("fail")
                }
            }

            override fun onFailure(call: Call<LikesResponse>, t: Throwable) {
                likesInterface.onDeleteLikesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryCheckLikes(diaryId: String){
        val likesRetrofitInterface = ApplicationClass.sRetrofit.create(LikesRetrofitInterface::class.java)
        likesRetrofitInterface.checklikes(Authorization = X_ACCESS_TOKEN, diaryId = diaryId).enqueue(object : Callback<CheckLikesResponse> {
            override fun onResponse(call: Call<CheckLikesResponse>, response: Response<CheckLikesResponse>) {
                if(response.isSuccessful){
                    likesInterface.onCheckLikesSuccess(response.body() as CheckLikesResponse)
                }else{
                    Log.d("fail","fail to check likes")
                    likesInterface.onCheckLikesFailure("fail")
                }
            }

            override fun onFailure(call: Call<CheckLikesResponse>, t: Throwable) {
                likesInterface.onCheckLikesFailure(t.message ?: "통신 오류")
            }
        })
    }


}
