package kr.co.app.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class UserEmailCheckNoTokenResponse(
    @SerializedName("information") val information: Information
)
data class Information(
    @SerializedName("email") val email: Boolean,
)
