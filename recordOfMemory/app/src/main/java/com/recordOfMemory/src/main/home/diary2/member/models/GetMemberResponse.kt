package com.recordOfMemory.src.main.home.diary2.member.models

import com.google.gson.annotations.SerializedName

data class GetMemberResponse(
    @SerializedName("userId") val userId: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("email") val email: String
)
