package kr.co.app.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostChangePasswordRequest(
    @SerializedName ("oldPassword") val oldPassword : String,
    @SerializedName ("newPassword") val newPassword : String
)
