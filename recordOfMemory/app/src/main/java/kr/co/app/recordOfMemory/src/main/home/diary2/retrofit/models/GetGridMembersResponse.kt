package kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetGridMembersResponse(
    @SerializedName("check") val check: Boolean,
    @SerializedName("information") val information: GridInformation
)
