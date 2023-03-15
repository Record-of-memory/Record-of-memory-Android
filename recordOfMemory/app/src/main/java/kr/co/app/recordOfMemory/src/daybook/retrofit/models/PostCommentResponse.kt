package kr.co.app.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostCommentResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information: CommentWriteInformation
)
data class CommentWriteInformation(
	@SerializedName("message") val message: String
)