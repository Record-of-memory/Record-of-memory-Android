package com.recordOfMemory.src.main.home.diary2.retrofit

import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse

interface Diary2Interface {
    fun onGetRecordsSuccess(response : GetRecordsResponse)
    fun onGetRecordsFailure(message: String)
}