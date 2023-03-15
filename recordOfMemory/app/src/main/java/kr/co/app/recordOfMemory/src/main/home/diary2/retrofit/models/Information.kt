package kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse

data class Information(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("diaryType") val diaryType: String,
    @SerializedName("users") val users: ArrayList<GetUserResponse>,
    @SerializedName("records") val records: ArrayList<GetMemberRecordResponse>
)