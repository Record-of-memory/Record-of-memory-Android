package com.recordOfMemory.src.main.calendar.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetCalendarDiariesResponse(
    @SerializedName("check") val check: Boolean,
    @SerializedName("information") val information: ArrayList<Information>
)

data class Information (
    @SerializedName("diaryId") val diaryId: String,
    @SerializedName("diaryName") val diaryName: String,
    @SerializedName("records") val records: ArrayList<Record>
)

data class Record (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("imgURL") val imgURL: String? = null,
    @SerializedName("date") val date: String,
    @SerializedName("user") val user: User,
    @SerializedName("likeCount") val likeCount: String,
    @SerializedName("commentCount") val commentCount: String
)

data class User (
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageURL") val imageURL: String? = null,
    @SerializedName("role") val role: String
)

data class DiaryName (
    @SerializedName("diaryId") val diaryId: String,
    @SerializedName("diaryName") val diaryName: String
)
