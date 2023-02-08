package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val myPageInterface: MyPageInterface) {
	private val auth:String="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjc1ODg0NTQ4LCJleHAiOjE2NzU4ODgxNDh9.hSobylv20NCH6taLiCEQzMQnkNIsvFvUWzh4JPeQhuL41M3AleW_p7XKi3fbwgqMJmHojrn8eNr_K9MNTryx_Q"
	private val myPageRetrofitInterface: MyPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

	fun tryPostSignOut(params: PostSignOutRequest){
		myPageRetrofitInterface.postSignOut(Authorization = auth, params = params).enqueue(object : Callback<PostSignOutResponse> {
			override fun onResponse(call: Call<PostSignOutResponse>, response: Response<PostSignOutResponse>) {
				if(response.isSuccessful){
					myPageInterface.onPostSignOutSuccess(response.body() as PostSignOutResponse)
				}else{
					Log.d("fail","fail to logout")
					myPageInterface.onPostSignOutFailure("${response.body()?.information?.message}")
				}
			}

			override fun onFailure(call: Call<PostSignOutResponse>, t: Throwable) {
				myPageInterface.onPostSignOutFailure(t.message ?: "통신 오류")
			}
		})
	}

	fun tryGetUsers(){
		myPageRetrofitInterface.getUsers("Bearer $auth").enqueue(object : Callback<GetUsersResponse> {
			override fun onResponse(call: Call<GetUsersResponse>, response: Response<GetUsersResponse>) {
				if(response.isSuccessful){
					myPageInterface.onGetUsersSuccess(response.body() as GetUsersResponse)
				}else{
					Log.d("fail","fail to get Information")
					myPageInterface.onGetUsersFailure("${response.body()?.information?.message}")
				}
			}

			override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
				myPageInterface.onGetUsersFailure(t.message ?: "통신 오류")
			}
		})
	}

}