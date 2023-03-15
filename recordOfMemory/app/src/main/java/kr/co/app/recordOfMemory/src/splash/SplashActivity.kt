package kr.co.app.recordOfMemory.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.config.BaseActivity
import kr.co.app.recordOfMemory.databinding.ActivitySplashBinding
import kr.co.app.recordOfMemory.src.main.MainActivity
import kr.co.app.recordOfMemory.src.main.signUp.SignUpActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            val accessToken = ApplicationClass.sSharedPreferences.getString(
                ApplicationClass.X_ACCESS_TOKEN, null)
//            val accessToken = null

//            val edit = ApplicationClass.sSharedPreferences.edit()
//            edit.remove("isFirst")
//            edit.apply()

            if (accessToken != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

            else {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
        }, 1500)
    }
}