package com.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName

data class GridInformation(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("users") val users: ArrayList<GridUser>,
)
