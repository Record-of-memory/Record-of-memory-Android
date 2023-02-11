package com.recordOfMemory.src.main.myPage.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostChangePasswordRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MyPageEditPasswordRetrofitInterface {
	//비밀번호 변경
	@POST("/api/users/me/password")
	fun postChangePassword(
		@Header("Authorization") Authorization: String,
		@Body params : PostChangePasswordRequest) : Call<BaseResponse>
}