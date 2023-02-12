package com.recordOfMemory.src.main.home.diary2.member.invite.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse

interface Diary2InviteInterface {
    fun onGetItemSize(itemSize: Int)
    fun onPostDiary2Invite(email : String)

    fun onPostDiary2InviteSuccess(response : BaseResponse)
    fun onPostDiary2InviteFailure(message: String)

    fun onGetUserEmailSuccess(response : GetUsersResponse)
    fun onGetUSerEmailFailure(message: String)
}