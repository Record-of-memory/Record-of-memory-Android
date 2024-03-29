package kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models

import com.google.gson.annotations.SerializedName

data class ResultDiaries(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("diaryType") val diaryType: String
)

