package kr.co.app.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostSignOutRequest(
	@SerializedName("refreshToken") val refreshToken: String
)
