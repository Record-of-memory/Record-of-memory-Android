package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyPageService(val myPageInterface: MyPageInterface) {
	val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"

	private val myPageRetrofitInterface: MyPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

	fun tryPostSignOut(params: PostSignOutRequest){
		myPageRetrofitInterface.postSignOut(Authorization = X_ACCESS_TOKEN, params = params).enqueue(object : Callback<PostSignOutResponse> {
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
		Log.e("error", "mypage")
		myPageRetrofitInterface.getUsers(X_ACCESS_TOKEN).enqueue(object : Callback<GetUsersResponse> {
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