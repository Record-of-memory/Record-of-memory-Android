package com.recordOfMemory.src.main.home.diary2.search.retrofit

import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse

interface Diary2SearchInterface {
    fun onGetRecordsSuccess(response : GetRecordsResponse)
    fun onGetRecordsFailure(message: String)
    fun onGetItemSize(itemSize: Int)
}