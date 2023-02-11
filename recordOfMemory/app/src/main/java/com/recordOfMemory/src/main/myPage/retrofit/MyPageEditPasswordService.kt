package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostChangePasswordRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageEditPasswordService(val myPageEditPasswordInterface: MyPageEditPasswordInterface) {

	val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
	val X_ACCESS_TOKEN = "Bearer $token"
	private val myPageEditPasswordRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageEditPasswordRetrofitInterface::class.java)

	fun tryPostChangePassword(params: PostChangePasswordRequest){

		myPageEditPasswordRetrofitInterface.postChangePassword(Authorization = X_ACCESS_TOKEN,params=params).enqueue(object :
			Callback<BaseResponse> {
			override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
				if(response.code()==200) {
					myPageEditPasswordInterface.onPostChangePasswordSuccess(response.body() as BaseResponse)
				}else{
					Log.d("fail","fail to change password")
					myPageEditPasswordInterface.onPostChangePasswordFailure("fail")
				}
			}

			override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
				myPageEditPasswordInterface.onPostChangePasswordFailure(t.message ?: "통신 오류")
			}
		})
	}
}