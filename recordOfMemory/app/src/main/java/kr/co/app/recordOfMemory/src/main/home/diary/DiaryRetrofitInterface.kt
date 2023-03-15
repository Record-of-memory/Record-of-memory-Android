package kr.co.app.recordOfMemory.src.main.home.diary

import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DiaryRetrofitInterface {
    @GET("/api/diaries")
    fun getDiaries(@Header("Authorization") Authorization: String) : Call<GetDiariesResponse>

    @POST("/api/diaries")
    fun postDiaries(@Header("Authorization") Authorization: String, @Body params: PostDiariesRequest) : Call<kr.co.app.recordOfMemory.config.BaseResponse>

    @GET("/api/users/me")
    fun getUsers(
        @Header("Authorization") Authorization: String,
    ):Call<GetUsersResponse>
}

