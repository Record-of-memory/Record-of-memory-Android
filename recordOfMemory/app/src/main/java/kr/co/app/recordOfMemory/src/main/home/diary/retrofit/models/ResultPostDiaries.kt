package kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models

import com.google.gson.annotations.SerializedName

data class ResultPostDiaries(
        @SerializedName("userId") val userId: Int,
        @SerializedName("jwt") val jwt: String
)