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

interface MyPageEditRetrofitInterface {
	//탈퇴 API
	@DELETE("/api/users/me")
	fun deleteUsers(
		@Header("Authorization") Authorization: String,
	):Call<DeleteUsersResponse>

	@Multipart
	@PATCH("/api/users/me")
	fun patchUsers(
		@Header("Authorization") Authorization: String,
		@Part profileImg: MultipartBody.Part?,
		@Part("nickname") nickname: RequestBody
	): Call<BaseResponse>
}