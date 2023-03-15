package kr.co.app.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostCommentRequest(
	@SerializedName("recordId") val recordId: String,
	@SerializedName("content") val content:String
)
