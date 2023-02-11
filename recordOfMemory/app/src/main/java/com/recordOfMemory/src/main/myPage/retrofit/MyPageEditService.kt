package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyPageEditService(val myPageEditInterface: MyPageEditInterface) {
	private val auth:String=
		"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsImlhdCI6MTY3NjA0ODM3OCwiZXhwIjoxNjc2MDUxOTc4fQ.myqr8mXMUKNUcPPNNhRYZcYmVKk89q98mHgAYN2S7abrV5ZNhn3lTSuTtN_gj61aRuUm-9pdVFuHS_N1M0yBAg"
	private val myPageEditRetrofitInterface: MyPageEditRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageEditRetrofitInterface::class.java)

	fun tryDeleteUsers(){
		myPageEditRetrofitInterface.deleteUsers("Bearer $auth").enqueue(object : Callback<DeleteUsersResponse> {
			override fun onResponse(call: Call<DeleteUsersResponse>, response: Response<DeleteUsersResponse>) {
				if(response.isSuccessful){
					myPageEditInterface.onDeleteUsersSuccess(response.body() as DeleteUsersResponse)
				}else{
					Log.d("fail","fail to delete Account")
					myPageEditInterface.onDeleteUsersFailure("fail")
				}
			}

			override fun onFailure(call: Call<DeleteUsersResponse>, t: Throwable) {
				myPageEditInterface.onDeleteUsersFailure(t.message ?: "통신 오류")
			}
		})
	}
}