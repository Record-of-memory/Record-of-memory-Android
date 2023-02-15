package com.recordOfMemory.src.main.calendar.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetCalendarDiariesRequest(
    @SerializedName("date") val date: String
)
