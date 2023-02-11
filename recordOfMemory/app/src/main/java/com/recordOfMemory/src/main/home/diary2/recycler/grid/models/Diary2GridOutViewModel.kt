package com.recordOfMemory.src.main.home.diary2.recycler.grid.models

import com.google.gson.annotations.SerializedName
<<<<<<<< HEAD:recordOfMemory/app/src/main/java/com/recordOfMemory/src/main/home/diary2/recycler/grid/models/Diary2GridOutViewModel.kt
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordResponse
========
>>>>>>>> guri:recordOfMemory/app/src/main/java/com/recordOfMemory/src/main/home/diary2/recycler/models/Diary2GridOutViewModel.kt


data class Diary2GridOutViewModel(
    @SerializedName("writer") var writer: String,
    @SerializedName("innerList") var innerList: ArrayList<GetRecordResponse>)
