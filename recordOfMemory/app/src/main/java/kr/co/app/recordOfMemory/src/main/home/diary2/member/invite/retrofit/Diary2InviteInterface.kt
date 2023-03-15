package kr.co.app.recordOfMemory.src.main.home.diary2.member.invite.retrofit

import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse

interface Diary2InviteInterface {
    fun onGetItemSize(itemSize: Int)
    fun onPostDiary2Invite(email : String)

    fun onPostDiary2InviteSuccess(response : kr.co.app.recordOfMemory.config.BaseResponse)
    fun onPostDiary2InviteFailure(message: String)

    fun onGetUserEmailSuccess(response : GetUsersResponse)
    fun onGetUSerEmailFailure(message: String)
}