package kr.co.app.recordOfMemory.src.main.home.diary2.likes

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostLikesRequest(
    @SerializedName("recordId") val recordId: String
) : Serializable
