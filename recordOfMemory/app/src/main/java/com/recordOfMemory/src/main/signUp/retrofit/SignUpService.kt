package com.recordOfMemory.src.main.signUp.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.config.ErrorResponse
import com.recordOfMemory.src.main.signUp.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SignUpService() {
    lateinit var signUpFragmentInterface: SignUpFragmentInterface
    lateinit var getRefreshTokenInterface: GetRefreshTokenInterface

    constructor(signUpFragmentInterface: SignUpFragmentInterface):this() {
        this.signUpFragmentInterface = signUpFragmentInterface
    }
    constructor(getRefreshTokenInterface: GetRefreshTokenInterface):this() {
        this.getRefreshTokenInterface = getRefreshTokenInterface
    }


    val ACCESS_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjc1OTQ5ODYzLCJleHAiOjE2NzU5NTM0NjN9.pnUXMs9s29D957lBDDDA7NNP1IuV3EGF-ETX8P7KXxW4GV_FGmEvPvVgaEbS9Xz4ueDGLKxi14nwoVf7aEsBYg"

    fun tryPostSignUp(postSignUpRequest: PostSignUpRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                signUpFragmentInterface.onPostSignUpSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                signUpFragmentInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSignIn(postSignInRequest: PostSignInRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postSignIn(postSignInRequest).enqueue(object :
            Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.code() == 200) {
                    signUpFragmentInterface.onPostSignInSuccess(response.body() as TokenResponse)
                    Log.d("code","200")
                } else {
                    // error body 가져오는 코드 필요함
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            ErrorResponse::class.java)
                        // 로그인 실패 에러 메시지
                        signUpFragmentInterface.onPostSignInFailure(error.information.message)
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        signUpFragmentInterface.onPostSignInFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                signUpFragmentInterface.onPostSignInFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostRefresh(postRefreshRequest: PostRefreshRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postRefresh(postRefreshRequest).enqueue(object :
            Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                getRefreshTokenInterface.onPostRefreshSuccess(response.body() as TokenResponse)
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                getRefreshTokenInterface.onPostRefreshFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostChangePassword(postChangePasswordRequest: PostChangePasswordRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postChangePassword(postChangePasswordRequest).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                signUpFragmentInterface.onPostChangePasswordSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                signUpFragmentInterface.onPostChangePasswordFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetUserEmailCheck(email : String){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.getUserEmailCheck(email, ACCESS_TOKEN).enqueue(object : Callback<UserEmailCheckResponse>{
            override fun onResponse(call: Call<UserEmailCheckResponse>, response: Response<UserEmailCheckResponse>) {
                if (response.code() == 200) {
                    //유저 정보 조회 성공
                    signUpFragmentInterface.onGetUserEmailCheckSuccess(response.body() as UserEmailCheckResponse)
                    Log.d("code","200")
                } else if (response.code() == 400) {
                    //유저 정보 조회 실패
                    signUpFragmentInterface.onGetUserEmailCheckFailure(response.body()!!.message)
                    Log.d("code","400")
                }
            }

            override fun onFailure(call: Call<UserEmailCheckResponse>, t: Throwable) {
                signUpFragmentInterface.onGetUserEmailCheckFailure(t.message ?: "통신 오류")
            }
        })
    }
}