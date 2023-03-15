package kr.co.app.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("check") val check: Boolean,
    @SerializedName("information") val information: Token

)
data class Token(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("tokenType") val tokenType: String
)
