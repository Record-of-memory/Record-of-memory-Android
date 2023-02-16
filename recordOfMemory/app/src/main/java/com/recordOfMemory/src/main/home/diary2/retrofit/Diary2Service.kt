package com.recordOfMemory.src.main.home.diary2.retrofit

import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.config.ErrorResponse
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.Diary2InviteInterface
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteRequest
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMembersResponse

import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Diary2Service() {
    lateinit var diary2Interface: Diary2Interface
    lateinit var diary2InviteInterface: Diary2InviteInterface
        val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
    val X_ACCESS_TOKEN = "Bearer $token"
//    val X_ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjc2MDU0NDIyLCJleHAiOjE2NzYwNTgwMjJ9.oxrL9v_gT1jfiONkZaVjSjLFhKYW37ii_uUi7QrAsbajgSxYSUDu_9o7FDJktlhy9__JXaZ1vZv5I_RTUJm4Hg"

    val diary2RetrofitInterface = ApplicationClass.sRetrofit.create(Diary2RetrofitInterface::class.java)

    constructor(diary2Interface: Diary2Interface) : this() {
        this.diary2Interface = diary2Interface
    }
    constructor(diary2InviteInterface: Diary2InviteInterface) : this() {
        this.diary2InviteInterface = diary2InviteInterface
    }

    fun tryPostDiaryInvite(params: PostDiary2InviteRequest) {
        diary2RetrofitInterface.postDiaries(Authorization = X_ACCESS_TOKEN, params = params).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if(response.code() == 200) {
                    diary2InviteInterface.onPostDiary2InviteSuccess(response.body() as BaseResponse)
                }
                else if(response.code() == 401) {
                    diary2InviteInterface.onPostDiary2InviteFailure("refreshToken")
                }
                else {
                    // error body 가져오는 코드 필요함
                    val gson = GsonBuilder().create()
                    val error = gson.fromJson(
                        response.errorBody()!!.string(),
                        ErrorResponse::class.java
                    )
                    // 로그인 실패 에러 메시지
                    diary2InviteInterface.onPostDiary2InviteFailure(error.information.message.split(": ")[1].split("\"")[0])
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                diary2InviteInterface.onPostDiary2InviteFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetUserEmail(email: String) {
        diary2RetrofitInterface.getUserEmail(Authorization = X_ACCESS_TOKEN, email = email).enqueue(object :
            Callback<GetUsersResponse> {
            override fun onResponse(call: Call<GetUsersResponse>, response: Response<GetUsersResponse>
            ) {
                println(response.code())
                if(response.code() == 200) {
                    diary2InviteInterface.onGetUserEmailSuccess(response.body() as GetUsersResponse)
                }
                else if(response.code() == 401) {
                    diary2InviteInterface.onGetUSerEmailFailure("refreshToken")
                }
                // error body 가져오는 코드 필요함
                else {
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            ErrorResponse::class.java
                        )
                        // 로그인 실패 에러 메시지
                        diary2InviteInterface.onGetUSerEmailFailure(
                            error.information.message.split(": ")[1].split(
                                "\""
                            )[0]
                        )
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        diary2InviteInterface.onGetUSerEmailFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
                diary2InviteInterface.onGetUSerEmailFailure(t.message ?: "통신 오류")
            }
        })
    }
//    fun tryGetRecords(diaryId: String) {
//        diary2RetrofitInterface.getRecords(Authorization = X_ACCESS_TOKEN, diaryId = diaryId).enqueue(object :
//            Callback<GetRecordsResponse> {
//            override fun onResponse(
//                call: Call<GetRecordsResponse>,
//                response: Response<GetRecordsResponse>
//            ) {
//                if(response.code() == 200) {
//                    diary2Interface.onGetRecordsSuccess(response.body() as GetRecordsResponse)
//                }
//                else if(response.code() == 401) {
//                    diary2Interface.onGetRecordsFailure("refreshToken")
//                }
//            }
//
//            override fun onFailure(call: Call<GetRecordsResponse>, t: Throwable) {
//                diary2Interface.onGetRecordsFailure(t.message ?: "통신 오류")
//            }
//        })
//    }
    fun tryLeaveDiary(diaryId: String) {
        diary2RetrofitInterface.deleteDiary(Authorization = X_ACCESS_TOKEN, diaryId = diaryId).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if(response.code() == 200) {
                    diary2Interface.onDeleteDiarySuccess(response.body() as BaseResponse)
                }
                else if(response.code() == 401) {
                    diary2Interface.onDeleteDiaryFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                diary2Interface.onDeleteDiaryFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetMembers(diaryId: String) {
        diary2RetrofitInterface.getMembers(Authorization = X_ACCESS_TOKEN, diaryId = diaryId).enqueue(object :
            Callback<GetMembersResponse> {
            override fun onResponse(
                call: Call<GetMembersResponse>,
                response: Response<GetMembersResponse>
            ) {
                if(response.code() == 200) {
                    diary2Interface.onGetMembersSuccess(response.body() as GetMembersResponse)
                }
                else if(response.code() == 401) {
                    diary2Interface.onGetMembersFailure("refreshToken")
                }
            }

            override fun onFailure(call: Call<GetMembersResponse>, t: Throwable) {
                diary2Interface.onGetMembersFailure(t.message ?: "통신 오류")
            }
        })
    }
}