package com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models

import com.google.gson.annotations.SerializedName

data class PostDiary2InviteRequest(
    @SerializedName("email") val email : String,
    @SerializedName("diaryId") val diaryId : String
)
