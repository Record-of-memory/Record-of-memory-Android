package com.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName

data class GridUser(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imgUrl") val imgUrl: String?,
    @SerializedName("records") val records: ArrayList<GridRecord>,
)
