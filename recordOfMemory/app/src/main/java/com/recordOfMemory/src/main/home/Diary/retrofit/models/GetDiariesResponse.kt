package com.recordOfMemory.src.main.home.Diary.retrofit.models

import com.google.gson.annotations.SerializedName
import com.recordOfMemory.src.main.home.Diary.ResultDiaries
import java.io.Serializable

data class GetDiariesResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<ResultDiaries>
) : Serializable

