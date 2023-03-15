package kr.co.app.recordOfMemory.src.main.home.diary2.likes

import com.google.gson.annotations.SerializedName

data class CheckLikesResponse(
    @SerializedName("check") val check: Boolean,
    @SerializedName("information") val information: Check
)

data class Check(
    @SerializedName("likeClicked") val likeClicked: Boolean
)
