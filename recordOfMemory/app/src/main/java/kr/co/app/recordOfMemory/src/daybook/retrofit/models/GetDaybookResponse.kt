package kr.co.app.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetDaybookResponse(
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information:DaybookInformation
)

data class DaybookInformation(
	@SerializedName("id") val id : String,
	@SerializedName("date") val date : String,
	@SerializedName("title") val title : String,
	@SerializedName("content") val content : String,
	@SerializedName("user") val user : String,
	@SerializedName("status") val status: String,
	@SerializedName("imgUrl") val imgUrl : String?,
	@SerializedName("diary") val diary : String,
	@SerializedName("likeCnt") val likeCnt :Int,
	@SerializedName("cmtCnt") val cmtCnt : Int
)