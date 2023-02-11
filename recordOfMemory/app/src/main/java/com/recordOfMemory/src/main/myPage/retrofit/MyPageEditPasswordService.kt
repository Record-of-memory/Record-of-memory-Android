package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.config.ErrorResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostChangePasswordRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

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
				}else if(response.code()==401){
					myPageEditPasswordInterface.onPostChangePasswordFailure("refreshToken")
				}else{
					// error body 가져오는 코드 필요함
					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(
							response.errorBody()!!.string(),
							ErrorResponse::class.java)
						myPageEditPasswordInterface.onPostChangePasswordFailure(error.information.message.split(": ")[1].split("\"")[0])
					} catch (e: IOException) {
						myPageEditPasswordInterface.onPostChangePasswordFailure(e.message ?: "통신 오류")
					}
				}
			}

			override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
				myPageEditPasswordInterface.onPostChangePasswordFailure(t.message ?: "통신 오류")
			}
		})
	}
}