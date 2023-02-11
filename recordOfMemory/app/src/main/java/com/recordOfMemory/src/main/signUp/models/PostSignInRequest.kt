package com.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class PostSignInRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)