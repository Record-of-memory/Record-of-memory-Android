package com.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostSignOutResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information: SignOutInformation
)

data class SignOutInformation(
	@SerializedName("message") val message: String
)
