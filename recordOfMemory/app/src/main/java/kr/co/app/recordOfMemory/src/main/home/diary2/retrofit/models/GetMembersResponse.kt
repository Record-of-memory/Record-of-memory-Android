package kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetMembersResponse(
    @SerializedName("check") val check: Boolean,
    @SerializedName("information") val information: Information
)
