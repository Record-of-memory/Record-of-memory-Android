package com.recordOfMemory.src.main.home.diary2.retrofit

import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.Diary2InviteInterface
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteRequest
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Diary2Service() {
    lateinit var diary2Interface: Diary2Interface
    lateinit var diary2InviteInterface: Diary2InviteInterface
        val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"
//    val X_ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjc2MDU0NDIyLCJleHAiOjE2NzYwNTgwMjJ9.oxrL9v_gT1jfiONkZaVjSjLFhKYW37ii_uUi7QrAsbajgSxYSUDu_9o7FDJktlhy9__JXaZ1vZv5I_RTUJm4Hg"


    constructor(diary2Interface: Diary2Interface) : this() {
        this.diary2Interface = diary2Interface
    }
    constructor(diary2InviteInterface: Diary2InviteInterface) : this() {
        this.diary2InviteInterface = diary2InviteInterface
    }

    fun tryPostDiariesInvite(params: PostDiary2InviteRequest) {

        val diary2RetrofitInterface = ApplicationClass.sRetrofit.create(Diary2RetrofitInterface::class.java)
        diary2RetrofitInterface.postDiaries(Authorization = X_ACCESS_TOKEN, params = params).enqueue(object :
            Callback<PostDiary2InviteResponse> {
            override fun onResponse(
                call: Call<PostDiary2InviteResponse>,
                response: Response<PostDiary2InviteResponse>
            ) {
                if(response.code() == 200) {
                    diary2InviteInterface.onPostDiary2InviteSuccess(response.body() as PostDiary2InviteResponse)
                }
                else if(response.code() == 401) {
                    diary2InviteInterface.onPostDiary2InviteFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<PostDiary2InviteResponse>, t: Throwable) {
                diary2InviteInterface.onPostDiary2InviteFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetUserEmail(email: String) {
        val diary2RetrofitInterface = ApplicationClass.sRetrofit.create(Diary2RetrofitInterface::class.java)
        diary2RetrofitInterface.getUserEmail(Authorization = X_ACCESS_TOKEN, email = email).enqueue(object :
            Callback<GetUsersResponse> {
            override fun onResponse(call: Call<GetUsersResponse>, response: Response<GetUsersResponse>
            ) {
                if(response.code() == 200) {
                    diary2InviteInterface.onGetUserEmailSuccess(response.body() as GetUsersResponse)
                }
                else if(response.code() == 401) {
                    diary2InviteInterface.onGetUSerEmailFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
                diary2InviteInterface.onGetUSerEmailFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetRecords(diaryId: String) {
        val diary2RetrofitInterface = ApplicationClass.sRetrofit.create(Diary2RetrofitInterface::class.java)
        diary2RetrofitInterface.getRecords(Authorization = X_ACCESS_TOKEN, diaryId = diaryId).enqueue(object :
            Callback<GetRecordsResponse> {
            override fun onResponse(
                call: Call<GetRecordsResponse>,
                response: Response<GetRecordsResponse>
            ) {
                if(response.code() == 200) {
                    diary2Interface.onGetRecordsSuccess(response.body() as GetRecordsResponse)
                }
                else if(response.code() == 401) {
                    diary2Interface.onGetRecordsFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<GetRecordsResponse>, t: Throwable) {
                diary2Interface.onGetRecordsFailure(t.message ?: "통신 오류")
            }
        })
    }
}