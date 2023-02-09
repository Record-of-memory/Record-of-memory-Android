package com.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Comment

data class GetCommentsResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information:CommentsInformation
)
data class CommentsInformation(
	@SerializedName("count") val count:Int,
	//@SerializedName("data") val data:ArrayList<Comment>
)
//
//data class Comment(
//	@SerializedName("nickname") val nickname: String,
//	@SerializedName("imageUrl") val imageUrl:String?,
//	@SerializedName("content") val content:String,
//	@SerializedName("createdAt") val createdAt:String
//)

