package kr.co.app.recordOfMemory.src.main.signUp.models

import com.google.gson.annotations.SerializedName

data class PostSignUpRequest (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname: String
    )
