package com.recordOfMemory.src.main.home.diary2.likes

import com.google.gson.annotations.SerializedName

data class LikesResponse(
    @SerializedName("message") val message: String
)