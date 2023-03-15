package kr.co.app.recordOfMemory.src.main.calendar.retrofit

import kr.co.app.recordOfMemory.src.main.calendar.retrofit.models.GetCalendarDiariesResponse

interface CalendarInterface {
    fun onGetRecordsDateSuccess(getCalendarDiariesResponse: GetCalendarDiariesResponse)
    fun onGetRecordsDateFailure(message: String)
}