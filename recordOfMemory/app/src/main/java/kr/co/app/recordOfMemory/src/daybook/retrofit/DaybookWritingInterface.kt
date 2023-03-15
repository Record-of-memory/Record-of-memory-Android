package kr.co.app.recordOfMemory.src.daybook.retrofit

import kr.co.app.recordOfMemory.config.BaseResponse

interface DaybookWritingInterface {
    fun onPatchRecordSuccess(response: BaseResponse)
    fun onPatchRecordFailure(message: String)

    fun onPostRecordSuccess(response: BaseResponse)
    fun onPostRecordFailure(response: String)
}