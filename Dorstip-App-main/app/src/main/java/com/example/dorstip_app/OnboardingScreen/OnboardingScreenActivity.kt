package com.example.dorstip_app.OnboardingScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.dorstip_app.LoginAndRegistration.LoginScreen
import com.example.dorstip_app.R

class OnboardingScreenActivity : AppCompatActivity() {

    private lateinit var OnboardingItemsAdapter: OnboardingItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_screen)
        setOnboardingItems()
    }

//    Each Onboarding item customization
    private fun setOnboardingItems(){
        OnboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.onboard_image1,
                    onboardingTitle = "Kies uw favoriet drankje!",
                    onboardingDescription = "Vind uw favoriete drankje op elk gewenst moment vanaf uw\n" +
                            "bestaande locatie gemakkelijk"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.onboard_image2,
                    onboardingTitle = "Verfris uzelf met een drankje !",
                    onboardingDescription = "Of het nu een spelletjesavond is of een lange dag na kantoor\n" +
                            "wij zijn er altijd om u op te frissen"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.onboard_image3,
                    onboardingTitle = "Snelste levering ooit",
                    onboardingDescription = "Omdat gekoelde drankjes altijd beter zijn"
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.vwOnboardScreen)
        onboardingViewPager.adapter = OnboardingItemsAdapter

        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)

        btnGetStarted.setOnClickListener {
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
    }
    }
}