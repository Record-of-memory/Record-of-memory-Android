package com.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName

data class DeleteUsersResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information: UsersInformation
)

data class DeleteUsersInformation(
	@SerializedName("message") override val message:String
):FailureInformation()