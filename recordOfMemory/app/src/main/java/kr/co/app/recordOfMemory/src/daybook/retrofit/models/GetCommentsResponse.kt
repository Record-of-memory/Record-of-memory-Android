package kr.co.app.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetCommentsResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information:CommentsInformation
)
data class CommentsInformation(
	@SerializedName("count") val count:Int,
	@SerializedName("data") val data:ArrayList<Comment>
)

data class Comment(
	@SerializedName("id") val id:String,
	@SerializedName("nickname") val nickname: String,
	@SerializedName("imageUrl") val imageUrl:String?,
	@SerializedName("content") var content:String,
	@SerializedName("createdAt") var createdAt:String,
	@SerializedName("isMyComment") val isMyComment:Boolean
) : Serializable

