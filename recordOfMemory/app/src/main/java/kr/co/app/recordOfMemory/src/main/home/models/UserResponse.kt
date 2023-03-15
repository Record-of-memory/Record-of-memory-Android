package kr.co.app.recordOfMemory.src.main.home.models

import com.google.gson.annotations.SerializedName
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.ResultDiaries

data class UserResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: ArrayList<ResultDiaries>
)
