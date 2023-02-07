package com.recordOfMemory.src.main.home.diary.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostDiaryResponse (
    @SerializedName("message") val message: String
)