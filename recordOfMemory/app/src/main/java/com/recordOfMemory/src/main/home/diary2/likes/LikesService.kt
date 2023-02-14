package com.recordOfMemory.src.main.home.diary2.likes

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikesService(val likesInterface: LikesInterface) {
        val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"

    fun tryPostLikes(postLikesRequest: PostLikesRequest){
        val likesRetrofitInterface = ApplicationClass.sRetrofit.create(LikesRetrofitInterface::class.java)
        likesRetrofitInterface.postlikes(Authorization = X_ACCESS_TOKEN, params = postLikesRequest).enqueue(object :
            Callback<LikesResponse> {
            override fun onResponse(call: Call<LikesResponse>, response: Response<LikesResponse>) {
                if(response.code() == 200) {
                    likesInterface.onPostLikesSuccess(response.body() as LikesResponse)
                }
                else if(response.code() == 401) {
                    likesInterface.onPostLikesFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<LikesResponse>, t: Throwable) {
                likesInterface.onPostLikesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteLikes(recordId: String){
        val likesRetrofitInterface = ApplicationClass.sRetrofit.create(LikesRetrofitInterface::class.java)
        likesRetrofitInterface.deletelikes(Authorization = X_ACCESS_TOKEN, recordId = recordId).enqueue(object : Callback<LikesResponse> {
            override fun onResponse(call: Call<LikesResponse>, response: Response<LikesResponse>) {
                if(response.code() == 200) {
                    likesInterface.onDeleteLikesSuccess(response.body() as LikesResponse)
                }
                else if(response.code() == 401) {
                    likesInterface.onDeleteLikesFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<LikesResponse>, t: Throwable) {
                likesInterface.onDeleteLikesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryCheckLikes(recordId: String){
        val likesRetrofitInterface = ApplicationClass.sRetrofit.create(LikesRetrofitInterface::class.java)
        likesRetrofitInterface.checklikes(Authorization = X_ACCESS_TOKEN, recordId = recordId).enqueue(object : Callback<CheckLikesResponse> {
            override fun onResponse(call: Call<CheckLikesResponse>, response: Response<CheckLikesResponse>) {
                if(response.code() == 200) {
                    likesInterface.onCheckLikesSuccess(response.body() as CheckLikesResponse)
                }
                else if(response.code() == 401) {
                    likesInterface.onCheckLikesFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<CheckLikesResponse>, t: Throwable) {
                likesInterface.onCheckLikesFailure(t.message ?: "통신 오류")
            }
        })
    }
}
