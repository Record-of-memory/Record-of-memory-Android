package com.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information: UsersInformation,
)

data class UsersInformation(
	@SerializedName("email") val email:String,
	@SerializedName("nickname") val nickname: String,
	@SerializedName("imageUrl") val imageUrl:String?,
	@SerializedName("role") val role:String
)