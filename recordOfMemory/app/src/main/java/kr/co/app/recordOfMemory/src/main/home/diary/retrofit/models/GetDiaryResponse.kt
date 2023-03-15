package kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetDiaryResponse(
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("diaryType") val diaryType : String,
) : Serializable
