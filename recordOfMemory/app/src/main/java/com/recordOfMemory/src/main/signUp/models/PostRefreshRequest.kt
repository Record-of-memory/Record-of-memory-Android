package com.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class PostRefreshRequest(
    @SerializedName("refreshToken") val refreshToken: String
)
