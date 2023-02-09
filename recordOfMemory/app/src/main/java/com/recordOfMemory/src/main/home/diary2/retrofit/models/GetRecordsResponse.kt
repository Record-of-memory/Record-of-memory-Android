package com.recordOfMemory.src.main.home.diary2.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetRecordsResponse(
    @SerializedName("check") val check : Boolean,
    @SerializedName("information") val information: ArrayList<GetRecordResponse>
)
