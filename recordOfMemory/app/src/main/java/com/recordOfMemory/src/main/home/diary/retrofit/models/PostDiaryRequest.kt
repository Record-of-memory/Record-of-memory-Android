package com.recordOfMemory.src.main.home.diary.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostDiaryRequest(
    @SerializedName("name") val name : String,
    @SerializedName("diaryType") val diaryType : String
)