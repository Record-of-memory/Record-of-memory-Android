package com.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class UserEmailCheckResponse(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("message") val message: String
)
