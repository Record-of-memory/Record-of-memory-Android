package com.recordOfMemory.src.main.home.diary2.recycler.grid.models

import com.google.gson.annotations.SerializedName
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMemberRecordResponse


data class Diary2GridOutViewModel(
    @SerializedName("writer") var writer: String,
    @SerializedName("innerList") var innerList: ArrayList<GetMemberRecordResponse>)
