package com.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName ("accessToken") val accessToken : String,
    @SerializedName ("refreshToken") val refreshToken : String,
    @SerializedName ("tokenType") val tokenType : String,
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String
)
