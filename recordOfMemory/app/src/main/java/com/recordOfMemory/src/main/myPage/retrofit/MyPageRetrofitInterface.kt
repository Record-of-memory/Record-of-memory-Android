package com.recordOfMemory.src.main.myPage.retrofit

import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {
	//로그아웃 API
	@POST("/auth/sign-out")
	fun postSignOut(
		@Header("Authorization") Authorization: String,
		@Body params: PostSignOutRequest,
	): Call<PostSignOutResponse>

	//마이페이지 정보 조회 API
	@GET("/api/users/me")
	fun getUsers(
		@Header("Authorization") Authorization: String,
	):Call<GetUsersResponse>

}