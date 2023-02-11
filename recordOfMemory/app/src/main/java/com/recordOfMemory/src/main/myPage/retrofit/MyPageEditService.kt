package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.ErrorResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyPageEditService(val myPageEditInterface: MyPageEditInterface) {
	val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
	val X_ACCESS_TOKEN = "Bearer $token"
	private val myPageEditRetrofitInterface: MyPageEditRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageEditRetrofitInterface::class.java)

	fun tryDeleteUsers(){
		myPageEditRetrofitInterface.deleteUsers(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<DeleteUsersResponse> {
			override fun onResponse(call: Call<DeleteUsersResponse>, response: Response<DeleteUsersResponse>) {
				if(response.isSuccessful){
					myPageEditInterface.onDeleteUsersSuccess(response.body() as DeleteUsersResponse)
				}else if(response.code()==401){
					myPageEditInterface.onDeleteUsersFailure("refreshToken")
				}else{
					// error body 가져오는 코드 필요함
					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(
							response.errorBody()!!.string(),
							ErrorResponse::class.java)
						myPageEditInterface.onDeleteUsersFailure(error.information.message.split(": ")[1].split("\"")[0])
					} catch (e: IOException) {
						myPageEditInterface.onDeleteUsersFailure(e.message ?: "통신 오류")
					}
				}
			}

			override fun onFailure(call: Call<DeleteUsersResponse>, t: Throwable) {
				myPageEditInterface.onDeleteUsersFailure(t.message ?: "통신 오류")
			}
		})
	}
}