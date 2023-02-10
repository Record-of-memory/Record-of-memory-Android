package com.recordOfMemory.src.main.home.models

import com.google.gson.annotations.SerializedName
import com.recordOfMemory.src.main.home.diary.ResultDiaries

data class UserResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: ArrayList<ResultDiaries>
)
