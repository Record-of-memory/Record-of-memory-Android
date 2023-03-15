package kr.co.app.recordOfMemory.src.main.signUp.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import kr.co.app.recordOfMemory.src.main.signUp.models.*
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

    //회원가입
    fun tryPostSignUp(postSignUpRequest: PostSignUpRequest){
        val signUpRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<kr.co.app.recordOfMemory.config.BaseResponse> {
            override fun onResponse(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, response: Response<kr.co.app.recordOfMemory.config.BaseResponse>) {
                Log.d("api연결", "회원가입")
                if (response.code() == 201) {
                    signUpFragmentInterface.onPostSignUpSuccess(response.body() as kr.co.app.recordOfMemory.config.BaseResponse)
                } else if (response.code() == 400) {
                    signUpFragmentInterface.onPostSignInFailure("회원가입 실패")
                } else {
                    // error body 가져오는 코드 필요함
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            kr.co.app.recordOfMemory.config.ErrorResponse::class.java)
                        // 로그인 실패 에러 메시지
                        signUpFragmentInterface.onPostSignInFailure(error.information.message)
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        signUpFragmentInterface.onPostSignInFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, t: Throwable) {
                signUpFragmentInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }

    //로그인
    fun tryPostSignIn(postSignInRequest: PostSignInRequest){
        val signUpRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postSignIn(postSignInRequest).enqueue(object :
            Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.code() == 200) {
                    signUpFragmentInterface.onPostSignInSuccess(response.body() as TokenResponse)
                    Log.d("code","200")
                } else if (response.code() == 400) {
                    signUpFragmentInterface.onPostSignInWrong("유저 로그인 실패")
                    Log.d("code","400")
                } else {
                    // error body 가져오는 코드 필요함
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            kr.co.app.recordOfMemory.config.ErrorResponse::class.java)
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

    //토큰 재발급
    fun tryPostRefresh(postRefreshRequest: PostRefreshRequest){
        val signUpRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postRefresh(postRefreshRequest).enqueue(object :
            Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if(response.code() == 200) {
                    getRefreshTokenInterface.onPostRefreshSuccess(response.body() as TokenResponse)
                }
                else {
                    // error body 가져오는 코드 필요함
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            kr.co.app.recordOfMemory.config.ErrorResponse::class.java)
                        // 토큰 갱신 실패 에러 메시지
                        getRefreshTokenInterface.onPostRefreshFailure(error.information.message)
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        getRefreshTokenInterface.onPostRefreshFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                getRefreshTokenInterface.onPostRefreshFailure(t.message ?: "통신 오류")
            }
        })
    }

    //비밀번호 변경
    fun tryPostChangePassword(postChangePasswordRequest: PostChangePasswordRequest){
        val signUpRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)

        val accessToken = "Bearer ${
            kr.co.app.recordOfMemory.config.ApplicationClass.sSharedPreferences.getString(
                kr.co.app.recordOfMemory.config.ApplicationClass.X_ACCESS_TOKEN,null).toString()}"

        if (accessToken != null) {
            signUpRetrofitInterface.postChangePassword(postChangePasswordRequest, accessToken).enqueue(object :
                Callback<kr.co.app.recordOfMemory.config.BaseResponse> {
                override fun onResponse(
                    call: Call<kr.co.app.recordOfMemory.config.BaseResponse>,
                    response: Response<kr.co.app.recordOfMemory.config.BaseResponse>
                ) {
                    Log.d("api연결", "비밀번호 변경")
                    if (response.code() == 200) {
                        //비밀번호 변경 성공
                        signUpFragmentInterface.onPostChangePasswordSuccess(response.body() as kr.co.app.recordOfMemory.config.BaseResponse)
                        Log.d("code","200")
                    } else if (response.code() == 400) {
                        //비밀번호 변경 실패
                        signUpFragmentInterface.onPostChangePasswordFailure("비밀번호 다름")
                        Log.d("code","400")
                    } else if (response.code() == 401) {
                        // 이 부분 에러가 날 확률이 높음
                        // tryPostRefresh()를 호출하고 tryPostChangePassword()를 호출하는 과정에서 트렌젝션이 충돌해 런타임 에러가 날 가능성이 있다.
                        // 토큰 재발급이 성공하면 이후에 onPostRefreshSuccess에서 다시 tryPostChangePassword()를 호출해야 한다.

                        //토큰 재발급
                        val X_REFRESH_TOKEN =
                            kr.co.app.recordOfMemory.config.ApplicationClass.sSharedPreferences.getString(
                                kr.co.app.recordOfMemory.config.ApplicationClass.X_REFRESH_TOKEN, "")
                                .toString()
                        tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
                        //재발급 후 자기 자신 한번 더 호출
                        tryPostChangePassword(postChangePasswordRequest)
                    } else {
                        // error body 가져오는 코드 필요함
                        val gson = GsonBuilder().create()
                        try {
                            val error = gson.fromJson(
                                response.errorBody()!!.string(),
                                kr.co.app.recordOfMemory.config.ErrorResponse::class.java)
                            // 로그인 실패 에러 메시지
                            signUpFragmentInterface.onPostSignInFailure(error.information.message)
                        } catch (e: IOException) {
                            // 통신 오류 에러 메시지
                            signUpFragmentInterface.onPostSignInFailure(e.message ?: "통신 오류")
                        }
                    }
                }

                override fun onFailure(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, t: Throwable) {
                    signUpFragmentInterface.onPostChangePasswordFailure(t.message ?: "통신 오류")
                }
            })
        }
    }

    //이메일로 유저 확인
    fun tryGetUserEmailNoTokenCheck(email : String){
        val signUpRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.getUserEmailCheckNoToken(email).enqueue(object : Callback<UserEmailCheckNoTokenResponse>{
            override fun onResponse(call: Call<UserEmailCheckNoTokenResponse>, response: Response<UserEmailCheckNoTokenResponse>) {
                Log.d("api연결", "이메일로 유저 확인(토큰필요X)")
                if (response.body()!!.information.email == true) {
                    //유저 정보 조회 성공
                    Log.d("#확인용#",response.toString())
                    signUpFragmentInterface.onGetUserEmailCheckNoTokenExist(response.body() as UserEmailCheckNoTokenResponse)
                    Log.d("code", "200")
                } else if (response.body()!!.information.email == false) {
                    //유저 정보 조회 실패
                    signUpFragmentInterface.onGetUserEmailCheckNoTokenNotExist("유저 중복 없음")
                    Log.d("code", "400")
                } else {
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            kr.co.app.recordOfMemory.config.ErrorResponse::class.java
                        )
                        // 로그인 실패 에러 메시지
                        signUpFragmentInterface.onGetUserEmailCheckNoTokenFailure(error.information.message)
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        signUpFragmentInterface.onGetUserEmailCheckNoTokenFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<UserEmailCheckNoTokenResponse>, t: Throwable) {
                signUpFragmentInterface.onGetUserEmailCheckNoTokenFailure(t.message ?: "통신 오류")
            }
        })
    }

    //이메일로 임시 비밀번호 발급
    fun tryPostResetPassword(postResetPasswordRequest: PostResetPasswordRequest){
        val signUpRetrofitInterface = kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postResetPassword(postResetPasswordRequest).enqueue(object :
            Callback<kr.co.app.recordOfMemory.config.BaseResponse> {
            override fun onResponse(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, response: Response<kr.co.app.recordOfMemory.config.BaseResponse>) {
                if (response.code() == 200) {
                    signUpFragmentInterface.onPostResetPasswordSuccess(response.body() as kr.co.app.recordOfMemory.config.BaseResponse)
                    Log.d("code","200")
                } else if (response.code() == 400) {
                    signUpFragmentInterface.onPostResetPasswordWrong("회원가입 필요 이메일")
                    Log.d("code","400")
                } else {
                    val gson = GsonBuilder().create()
                    try {
                        val error = gson.fromJson(
                            response.errorBody()!!.string(),
                            kr.co.app.recordOfMemory.config.ErrorResponse::class.java)
                        // 임시 비밀번호 발급 실패 에러 메시지
                        signUpFragmentInterface.onPostResetPasswordFailure(error.information.message)
                    } catch (e: IOException) {
                        // 통신 오류 에러 메시지
                        signUpFragmentInterface.onPostResetPasswordFailure(e.message ?: "통신 오류")
                    }
                }
            }

            override fun onFailure(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, t: Throwable) {
                signUpFragmentInterface.onPostResetPasswordFailure(t.message ?: "통신 오류")
            }
        })
    }
}