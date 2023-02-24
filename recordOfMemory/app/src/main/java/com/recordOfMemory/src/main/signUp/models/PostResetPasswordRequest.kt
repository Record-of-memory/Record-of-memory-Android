package com.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class PostResetPasswordRequest (
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String
)