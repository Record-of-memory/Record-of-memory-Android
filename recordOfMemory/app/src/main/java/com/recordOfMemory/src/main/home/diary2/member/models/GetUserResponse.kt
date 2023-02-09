package com.recordOfMemory.src.main.home.diary2.member.models

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("role") val role: String
)
