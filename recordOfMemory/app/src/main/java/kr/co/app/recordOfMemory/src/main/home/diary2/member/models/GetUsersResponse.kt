package kr.co.app.recordOfMemory.src.main.home.diary2.member.models

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(
    @SerializedName("check") val check : Boolean,
    @SerializedName("information") val information: GetUserResponse
)
