package com.recordOfMemory.src.main.myPage.retrofit

import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import retrofit2.Call
import retrofit2.http.*

interface MyPageEditRetrofitInterface {
	//탈퇴 API
	@DELETE("/api/users/me")
	fun deleteUsers(
		@Header("Authorization") Authorization: String,
	):Call<DeleteUsersResponse>

}