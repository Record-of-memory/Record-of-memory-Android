package com.recordOfMemory.src.main.myPage.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

	@Multipart
	@PATCH("/api/users/me")
	fun patchUsers(
		@Header("Authorization") Authorization: String,
		@Part profileImg: MultipartBody.Part?,
		@Part("nickname") nickname: String
	): Call<BaseResponse>
}