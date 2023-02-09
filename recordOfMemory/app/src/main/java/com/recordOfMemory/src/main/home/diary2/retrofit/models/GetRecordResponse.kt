package com.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetRecordResponse(
    @SerializedName("id") val id : String,
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String,
    @SerializedName("imgUrl") val imgUrl : String,
    @SerializedName("date") val date : String,
    @SerializedName("user") val user : String,
    @SerializedName("status") val status: String,
    @SerializedName("diary") val diary : String,
    @SerializedName("likeCnt") val likeCnt : String,
    @SerializedName("cmtCnt") val cmtCnt : String
) : Serializable
