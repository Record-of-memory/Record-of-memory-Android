package kr.co.app.recordOfMemory.src.main.home.diary2.retrofit

import kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models.GetGridMembersResponse
import kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models.GetMembersResponse

interface Diary2Interface {
    fun onDeleteDiarySuccess(response : kr.co.app.recordOfMemory.config.BaseResponse)
    fun onDeleteDiaryFailure(message: String)

    fun onGetMembersSuccess(response : GetMembersResponse)
    fun onGetMembersFailure(message: String)

    fun onGetGridMembersSuccess(response : GetGridMembersResponse)
    fun onGetGridMembersFailure(message: String)

}