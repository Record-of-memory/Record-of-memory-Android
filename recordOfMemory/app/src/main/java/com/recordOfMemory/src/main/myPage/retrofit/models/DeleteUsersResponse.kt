package com.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName

data class DeleteUsersResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information: DeleteUsersInformation
)

data class DeleteUsersInformation(
	@SerializedName("message") val message:String
)