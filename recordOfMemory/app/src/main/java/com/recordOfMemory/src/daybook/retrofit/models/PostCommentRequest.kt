package com.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostCommentRequest(
	@SerializedName("recordId") val recordId: Int,
	@SerializedName("content") val content:String
)
