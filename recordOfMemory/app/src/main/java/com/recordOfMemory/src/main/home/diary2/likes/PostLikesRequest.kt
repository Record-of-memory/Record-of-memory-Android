package com.recordOfMemory.src.main.home.diary2.likes

import com.google.gson.annotations.SerializedName

data class PostLikesRequest(
    @SerializedName("recordId") val recordId: Int
)
