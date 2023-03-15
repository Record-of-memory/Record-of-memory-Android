package kr.co.app.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName

data class PatchUserRequest(
    @SerializedName("nickname") val nickname: String,
)
