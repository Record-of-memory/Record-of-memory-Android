package kr.co.app.recordOfMemory.src.main.home.diary

import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse


interface DiaryFragmentInterface {
    fun onGetDiariesSuccess(response: GetDiariesResponse)

    fun onGetDiariesFailure(message: String)

    fun onPostDiariesSuccess(response: kr.co.app.recordOfMemory.config.BaseResponse)

    fun onPostDiariesFailure(message: String)

    fun onGetUsersSuccess(response: GetUsersResponse)

    fun onGetUsersFailure(message: String)
}