package com.recordOfMemory.src.main.home.diary

import com.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse


interface DiaryFragmentInterface {
    fun onGetDiariesSuccess(response: GetDiariesResponse)

    fun onGetDiariesFailure(message: String)

    fun onPostDiariesSuccess(response: BaseResponse)

    fun onPostDiariesFailure(message: String)

    fun onGetUsersSuccess(response: GetUsersResponse)

    fun onGetUsersFailure(message: String)
}