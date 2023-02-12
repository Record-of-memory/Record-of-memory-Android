package com.recordOfMemory.src.main.home.diary.retrofit.models

import com.google.gson.annotations.SerializedName
import com.recordOfMemory.config.BaseResponse

data class PostDiariesResponse(
    @SerializedName("result") val result: ResultPostDiaries
) : BaseResponse()