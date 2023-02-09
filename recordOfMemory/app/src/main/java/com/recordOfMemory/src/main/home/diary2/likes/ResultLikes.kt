package com.recordOfMemory.src.main.home.diary2.likes

import com.google.gson.annotations.SerializedName

data class ResultLikes(
    @SerializedName("userId") val userId: Int,
    @SerializedName("jwt") val jwt: String
    )
