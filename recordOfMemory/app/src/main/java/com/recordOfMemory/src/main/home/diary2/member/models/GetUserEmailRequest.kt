package com.recordOfMemory.src.main.home.diary2.member.models

import com.google.gson.annotations.SerializedName

data class GetUserEmailRequest(
    @SerializedName("email") val email : String
)
