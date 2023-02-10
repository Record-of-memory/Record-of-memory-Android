package com.recordOfMemory.src.main.home.diary2.likes

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckLikesResponse(
    @SerializedName("likeClicked") val likeClicked: Boolean
) : Serializable
