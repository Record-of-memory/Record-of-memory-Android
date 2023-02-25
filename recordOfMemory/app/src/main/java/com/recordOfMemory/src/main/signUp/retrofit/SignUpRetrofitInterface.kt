package com.recordOfMemory.src.main.signUp.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.signUp.models.*
import retrofit2.Call
import retrofit2.http.*

interface SignUpRetrofitInterface {
    //유저 회원가입
    @POST("/auth/sign-up")
    fun postSignUp(@Body params: PostSignUpRequest) : Call<BaseResponse>

    //유저 로그인
    @POST("/auth/sign-in")
    fun postSignIn(@Body params : PostSignInRequest) : Call<TokenResponse>

    //토큰 갱신
    @POST("/auth/refresh")
    fun postRefresh(@Body params : PostRefreshRequest) : Call<TokenResponse>

    //비밀번호 변경
    @POST("/api/users/me/password")
    fun postChangePassword(
        @Body params : PostChangePasswordRequest,
        @Header("Authorization") Authorization: String
    ) : Call<BaseResponse>

    //이메일로 유저 확인(토큰 필요)

    @GET("/api/users")
    fun getUserEmailCheck(
        @Query("email") email : String,
        @Header("Authorization") Authorization: String
    ) : Call<UserEmailCheckResponse>

    //이메일로 유저 확인(토큰 필요X)
    @GET("/auth/check/{email}")
    fun getUserEmailCheckNoToken(
        @Path("email") email : String
    ): Call<UserEmailCheckNoTokenResponse>

    //이메일로 임시 비밀번호 발급
    @POST("/auth/send")
    fun postResetPassword(@Body params : PostResetPasswordRequest) : Call<BaseResponse>

}