package com.recordOfMemory.src.main.home.diary2.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteRequest
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetGridMembersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMembersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse
import retrofit2.Call
import retrofit2.http.*

interface Diary2RetrofitInterface {
    // 다이어리 생성 API
    @POST("/api/diaries/invite")
    fun postDiaries(
        @Header("Authorization") Authorization: String,
        @Body params: PostDiary2InviteRequest
    ) : Call<BaseResponse>


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

    @DELETE("/api/diaries/{diaryId}")
    fun deleteDiary(
        @Header("Authorization") Authorization: String,
        @Path("diaryId") diaryId : String
    ): Call<BaseResponse>

    @GET("/api/diaries/{diaryId}")
    fun getMembers(
        @Header("Authorization") Authorization: String,
        @Path("diaryId") diaryId : String
    ) :  Call<GetMembersResponse>

    @GET("/api/records/grid/{diaryId}")
    fun getGridMembers(
        @Header("Authorization") Authorization: String,
        @Path("diaryId") diaryId : String
    ) :  Call<GetGridMembersResponse>
}