package com.recordOfMemory.src.main.signUp

import com.recordOfMemory.src.main.signUp.models.*
import retrofit2.Call
import retrofit2.http.*

interface SignUpRetrofitInterface {
    //유저 회원가입
    @POST("/auth/sign-up")
    fun postSignUp(@Body params: PostSignUpRequest) : Call<SignUpResponse>

    //유저 로그인
    @POST("/auth/sign-in")
    fun postSignIn(@Body params : PostSignInRequest) : Call<SignInResponse>

    //토큰 갱신
    @POST("/auth/refresh")
    fun postRefresh(@Body params : PostRefreshRequest) : Call<RefreshResponse>

    //비밀번호 변경
    @POST("/api/users/me/password")
    fun postChangePassword(@Body params : PostChangePasswordRequest) : Call<ChangePasswordResponse>

    @GET("/api/users")
    fun getUserEmailCheck(
        @Query("email") email : String,
        @Header("Authorization") Authorization: String
    ) : Call<UserEmailCheckResponse>
}