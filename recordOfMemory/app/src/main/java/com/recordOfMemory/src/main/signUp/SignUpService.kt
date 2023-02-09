package com.recordOfMemory.src.main.signUp

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.main.signUp.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpService(val signUpFragmentInterface: SignUpFragmentInterface) {

    val ACCESS_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjc1OTQ5ODYzLCJleHAiOjE2NzU5NTM0NjN9.pnUXMs9s29D957lBDDDA7NNP1IuV3EGF-ETX8P7KXxW4GV_FGmEvPvVgaEbS9Xz4ueDGLKxi14nwoVf7aEsBYg"

    fun tryPostSignUp(postSignUpRequest: PostSignUpRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                signUpFragmentInterface.onPostSignUpSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                signUpFragmentInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSignIn(postSignInRequest: PostSignInRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postSignIn(postSignInRequest).enqueue(object :
            Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.code() == 200) {
                    signUpFragmentInterface.onPostSignInSuccess(response.body() as SignInResponse)
                    Log.d("code","200")
                } else if (response.code() == 400) {
                    signUpFragmentInterface.onPostSignInFailure(response.body()!!.message)
                    Log.d("code","400")
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                signUpFragmentInterface.onPostSignInFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostRefresh(postRefreshRequest: PostRefreshRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postRefresh(postRefreshRequest).enqueue(object :
            Callback<RefreshResponse> {
            override fun onResponse(call: Call<RefreshResponse>, response: Response<RefreshResponse>) {
                signUpFragmentInterface.onPostRefreshSuccess(response.body() as RefreshResponse)
            }

            override fun onFailure(call: Call<RefreshResponse>, t: Throwable) {
                signUpFragmentInterface.onPostRefreshFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostChangePassword(postChangePasswordRequest: PostChangePasswordRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postChangePassword(postChangePasswordRequest).enqueue(object :
            Callback<ChangePasswordResponse> {
            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                signUpFragmentInterface.onPostChangePasswordSuccess(response.body() as ChangePasswordResponse)
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
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