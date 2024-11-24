package com.example.dorstip_app.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dorstip_app.Login.LoginActivity
import com.example.dorstip_app.Login.LoginManager
import com.example.dorstip_app.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileName: TextView = findViewById(R.id.profileName)
        val profileEmail: TextView = findViewById(R.id.profileEmail)
        val ibBack: ImageButton = findViewById(R.id.ibBack)

        ibBack.setOnClickListener { finish() }

        val loginManager = LoginManager(this)
        loginManager.getProfile { success, response ->
            runOnUiThread {
                if (success && response != null) {
                    try {
                        val firstName = response.getString("first_name")
                        val lastName = response.getString("last_name")
                        val email = response.getString("email")

                        profileName.text = "Name: $firstName $lastName"
                        profileEmail.text = "Email: $email"
                    } catch (e: Exception) {
                        profileName.text = "Error parsing profile data"
                        profileEmail.text = ""
                        Log.e("ProfileActivity", "Error: ${e.message}")
                    }
                } else {
                    profileName.text = "Error fetching profile"
                    profileEmail.text = ""
                }
            }
        }

        // Logout button
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Clear the token
            loginManager.clearToken()

            // Redirect to login screen
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }


    }

}
