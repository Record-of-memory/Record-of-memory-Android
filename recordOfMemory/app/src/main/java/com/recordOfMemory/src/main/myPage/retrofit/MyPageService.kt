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
		"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsImlhdCI6MTY3NTk3OTMyNiwiZXhwIjoxNjc1OTgyOTI2fQ.3rX1Q4XPvw7FC-I6idDNc6h-0_fTDO5fjoV2zGKiVzjLsUMaTJA4nYBQ8UhUZ-rdnfyhdxUICc7UfMlYwBo5kQ"
	private val myPageRetrofitInterface: MyPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

	fun tryPostSignOut(params: PostSignOutRequest){
		myPageRetrofitInterface.postSignOut(Authorization = "Bearer $auth", params = params).enqueue(object : Callback<PostSignOutResponse> {
			override fun onResponse(call: Call<PostSignOutResponse>, response: Response<PostSignOutResponse>) {
				if(response.code()==200){
					myPageInterface.onPostSignOutSuccess(response.body() as PostSignOutResponse)
				}else{
					Log.d("fail","fail to logout")
					myPageInterface.onPostSignOutFailure("fail")
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
					Log.d("인터페이스",myPageInterface.toString())
					Log.d("응답",response.body().toString())
					myPageInterface.onGetUsersSuccess(response.body() as GetUsersResponse)
				}else{
					Log.d("fail","fail to get Information")
					myPageInterface.onGetUsersFailure("fail")
				}
			}

			override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
				myPageInterface.onGetUsersFailure(t.message ?: "통신 오류")
			}
		})
	}

}