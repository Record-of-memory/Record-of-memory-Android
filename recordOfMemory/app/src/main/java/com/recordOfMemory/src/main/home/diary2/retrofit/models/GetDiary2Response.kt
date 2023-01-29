package com.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetDiary2Response(
    @SerializedName("itemId") val itemId : String,
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String,
    @SerializedName("date") val date : String,
    @SerializedName("writer") val writer : String,
    @SerializedName("imgUrl") val imgUrl : String
)
