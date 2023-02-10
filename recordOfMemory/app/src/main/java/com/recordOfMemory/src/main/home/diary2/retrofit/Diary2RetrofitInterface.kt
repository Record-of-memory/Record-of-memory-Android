package com.recordOfMemory.src.main.home.diary2.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteRequest
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Diary2RetrofitInterface {
    // 다이어리 생성 API
    @POST("/api/diaries/invite")
    fun postDiaries(
        @Header("Authorization") Authorization: String,
        @Body params: PostDiary2InviteRequest
    ) : Call<PostDiary2InviteResponse>

    // 이메일로 유저 조회
    @GET("/api/users")
    fun getUserEmail(
        @Header("Authorization") Authorization: String,
        @Query("email") email: String
    ) : Call<GetUsersResponse>

    @GET("/api/records/{diaryId}")
    fun getRecords(
        @Header("Authorization") Authorization: String,
        @Path("diaryId") diaryId : String
    ): Call<GetRecordsResponse>
}