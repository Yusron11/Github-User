package com.dicoding.githubuser.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.githubuser.R
import com.dicoding.githubuser.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            toMain()
        }, 2000L)
    }

    private fun toMain () {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}