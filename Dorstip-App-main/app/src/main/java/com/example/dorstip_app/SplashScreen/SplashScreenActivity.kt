package com.example.dorstip_app.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.dorstip_app.OnboardingScreen.OnboardingScreenActivity
import com.example.dorstip_app.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        Initializing and animation of the textview
        val tvSplashScreenTitle: TextView = findViewById(R.id.tvSplashScreenTitle)
        val tvSplashScreenSubtitle: TextView = findViewById(R.id.tvSplashScreenSubtitle)
        val tvSplashScreenDot: TextView = findViewById(R.id.tvSplashScreenDot)
        tvSplashScreenTitle.alpha = 0f
        tvSplashScreenSubtitle.alpha = 0f
        tvSplashScreenDot.alpha = 0f

        // Animate the TextViews
        tvSplashScreenTitle.animate().setDuration(1500).alpha(1f)
        tvSplashScreenSubtitle.animate().setDuration(1500).alpha(1f)
        tvSplashScreenDot.animate().setDuration(1500).alpha(1f).withEndAction {
            // Delay for 1,5 seconds before starting MainActivity
            Handler().postDelayed({
                val i = Intent(this, OnboardingScreenActivity::class.java)
                startActivity(i)
                //transition animation
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }, 1500)
        }
    }
}