package com.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName
import com.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse

data class GetMembersResponse(
    @SerializedName("check") val check: Boolean,
    @SerializedName("information") val information: Information
)
