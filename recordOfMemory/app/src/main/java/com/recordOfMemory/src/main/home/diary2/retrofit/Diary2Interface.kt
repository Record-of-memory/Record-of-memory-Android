package com.recordOfMemory.src.main.home.diary2.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMembersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse

interface Diary2Interface {
    fun onGetRecordsSuccess(response : GetRecordsResponse)
    fun onGetRecordsFailure(message: String)

    fun onDeleteDiarySuccess(response : BaseResponse)
    fun onDeleteDiaryFailure(message: String)

    fun onGetMembersSuccess(response : GetMembersResponse)
    fun onGetMembersFailure(message: String)

}