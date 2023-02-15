package com.recordOfMemory.src.main.calendar.retrofit

import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.ErrorResponse
import com.recordOfMemory.src.main.calendar.retrofit.models.GetCalendarDiariesRequest
import com.recordOfMemory.src.main.calendar.retrofit.models.GetCalendarDiariesResponse
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class CalendarService(val calendarInterface: CalendarInterface) {
    val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"

    val calendarRetrofitInterface = ApplicationClass.sRetrofit.create(CalendarRetrofitInterface::class.java)


    fun tryGetRecordsDate(getCalendarDiariesRequest: GetCalendarDiariesRequest) {
        calendarRetrofitInterface.getRecordsDate(Authorization = X_ACCESS_TOKEN, date = getCalendarDiariesRequest.date).enqueue(object :
            retrofit2.Callback<GetCalendarDiariesResponse> {
            override fun onResponse(
                call: Call<GetCalendarDiariesResponse>,
                response: Response<GetCalendarDiariesResponse>
            ) {
                if(response.code() == 200) {
                    calendarInterface.onGetRecordsDateSuccess(response.body() as GetCalendarDiariesResponse)
                }
                else if(response.code() == 401) {
                    calendarInterface.onGetRecordsDateFailure("refreshToken")
                }
                else {
                    // error body 가져오는 코드 필요함
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            ErrorResponse::class.java
                        )
                        // 로그인 실패 에러 메시지
                        calendarInterface.onGetRecordsDateFailure(error.information.message.split(": ")[1].split("\"")[0])
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        calendarInterface.onGetRecordsDateFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<GetCalendarDiariesResponse>, t: Throwable) {
                calendarInterface.onGetRecordsDateFailure(t.message ?: "통신 오류")
            }
        })
    }
}