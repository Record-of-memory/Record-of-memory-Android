package com.recordOfMemory.src.main.home.Diary.retrofit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetDiaryResponse(
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("diaryType") val diaryType : String,
) : Serializable
