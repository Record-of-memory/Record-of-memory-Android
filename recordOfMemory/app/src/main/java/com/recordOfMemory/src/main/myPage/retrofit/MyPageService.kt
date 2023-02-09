package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.myPage.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyPageService(val myPageInterface: MyPageInterface) {
	private val auth:String=
		"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsImlhdCI6MTY3NTk3NjM2NywiZXhwIjoxNjc1OTc5OTY3fQ.a_JmP4aX7xGoF2nZJFAhlyAY46QEbE_Hndm_fCmZjJRXm-DwJEtxEzVV5blu7xPpLhMg25EaIghAOjhsRXt5_A"
	private val myPageRetrofitInterface: MyPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

	fun tryPostSignOut(params: PostSignOutRequest){
		myPageRetrofitInterface.postSignOut(Authorization = "Bearer $auth", params = params).enqueue(object : Callback<PostSignOutResponse> {
			override fun onResponse(call: Call<PostSignOutResponse>, response: Response<PostSignOutResponse>) {
				if(response.code()==200){
					myPageInterface.onPostSignOutSuccess(response.body() as PostSignOutResponse)
				}else{
					/////여기 에러 안받아짐............
					Log.d("fail","fail to logout")

					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(response.errorBody()!!.string(), PostSignOutResponse::class.java)

						myPageInterface.onPostSignOutFailure("fail")
					} catch (e: IOException) {
						// 통신 오류 에러 메시지
						myPageInterface.onPostSignOutFailure(e.message ?: "통신 오류")
					}
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
				if(response.code()==200){
					myPageInterface.onGetUsersSuccess(response.body() as GetUsersResponse)
				}else{
					/////여기 에러 안받아짐............
					Log.d("fail","fail to get Information")
					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(response.errorBody()!!.string(), GetUsersResponse::class.java)

						Log.d("error", error.toString())
						myPageInterface.onGetUsersFailure("fail")
					} catch (e: IOException) {
						// 통신 오류 에러 메시지
						myPageInterface.onGetUsersFailure(e.message ?: "통신 오류")
					}
				}
			}

			override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
				myPageInterface.onGetUsersFailure(t.message ?: "통신 오류")
			}
		})
	}

}