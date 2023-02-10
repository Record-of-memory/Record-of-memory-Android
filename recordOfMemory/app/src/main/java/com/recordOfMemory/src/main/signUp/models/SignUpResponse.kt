package com.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message") val message: String
)

