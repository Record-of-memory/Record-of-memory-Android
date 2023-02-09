package com.recordOfMemory.src.main.myPage.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyPageEditService(val myPageEditInterface: MyPageEditInterface) {
	private val auth:String="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMCIsImlhdCI6MTY3NTk0ODM5MSwiZXhwIjoxNjc1OTUxOTkxfQ.V47pUxb2EUPcGpfA4du3mUctLP0Tm-R9GyVoKC7kBm1Omd01jYzD9ENqTbwhKnPBF4UzkusjlkwbN2HsiYjLCg"
	private val myPageEditRetrofitInterface: MyPageEditRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageEditRetrofitInterface::class.java)

	fun tryDeleteUsers(){
		myPageEditRetrofitInterface.deleteUsers("Bearer $auth").enqueue(object : Callback<DeleteUsersResponse> {
			override fun onResponse(call: Call<DeleteUsersResponse>, response: Response<DeleteUsersResponse>) {
				if(response.isSuccessful){
					myPageEditInterface.onDeleteUsersSuccess(response.body() as DeleteUsersResponse)
				}else{
					///////////////여기 안 받아짐
					Log.d("fail","fail to delete Account")

					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(response.errorBody()!!.string(),DeleteUsersResponse::class.java)
						// 로그인 실패 에러 메시지
						myPageEditInterface.onDeleteUsersFailure("fail")
					} catch (e: IOException) {
						// 통신 오류 에러 메시지
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