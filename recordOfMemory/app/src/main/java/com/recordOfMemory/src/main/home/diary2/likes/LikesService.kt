package com.recordOfMemory.src.main.home.diary2.likes

import com.recordOfMemory.config.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikesService(val likesInterface: LikesInterface) {
    fun tryPostLikes(postLikesRequest: PostLikesRequest){
        val likesRetrofitInterface = ApplicationClass.sRetrofit.create(LikesRetrofitInterface::class.java)
        likesRetrofitInterface.postlikes(Authorization = "", params = postLikesRequest).enqueue(object :
            Callback<LikesResponse> {
            override fun onResponse(call: Call<LikesResponse>, response: Response<LikesResponse>) {
                likesInterface.onPostLikesSuccess(response.body() as LikesResponse)
            }

            override fun onFailure(call: Call<LikesResponse>, t: Throwable) {
                likesInterface.onPostLikesFailure(t.message ?: "통신 오류")
            }
        })
    }
}
