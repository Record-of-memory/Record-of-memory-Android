package kr.co.app.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName

data class DeleteCommentRequest(
    @SerializedName("commentId") val commentId:String
)
