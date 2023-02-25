package com.recordOfMemory.src.main.home.diary2.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetGridMembersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMembersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse

interface Diary2Interface {
    fun onDeleteDiarySuccess(response : BaseResponse)
    fun onDeleteDiaryFailure(message: String)

    fun onGetMembersSuccess(response : GetMembersResponse)
    fun onGetMembersFailure(message: String)

    fun onGetGridMembersSuccess(response : GetGridMembersResponse)
    fun onGetGridMembersFailure(message: String)

}