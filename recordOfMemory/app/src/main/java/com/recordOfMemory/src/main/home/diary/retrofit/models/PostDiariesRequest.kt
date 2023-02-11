package com.recordOfMemory.src.main.home.diary.retrofit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostDiariesRequest(
    @SerializedName("name") val name : String,
    @SerializedName("diaryType") val diaryType : String,
) : Serializable
