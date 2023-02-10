package com.recordOfMemory.src.main.home.diary2.recycler.models

import com.google.gson.annotations.SerializedName


data class Diary2GridOutViewModel(
    @SerializedName("writer") var writer: String,
    @SerializedName("innerList") var innerList: ArrayList<GetDiary2Response>)
