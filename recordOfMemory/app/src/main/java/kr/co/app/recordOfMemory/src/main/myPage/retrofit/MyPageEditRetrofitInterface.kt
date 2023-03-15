package kr.co.app.recordOfMemory.src.main.myPage.retrofit

import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
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