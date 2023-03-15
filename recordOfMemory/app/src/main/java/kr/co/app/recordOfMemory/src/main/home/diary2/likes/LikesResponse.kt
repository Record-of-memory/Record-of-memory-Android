package kr.co.app.recordOfMemory.src.main.home.diary2.likes

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LikesResponse(
    @SerializedName("message") val message: String
) : Serializable