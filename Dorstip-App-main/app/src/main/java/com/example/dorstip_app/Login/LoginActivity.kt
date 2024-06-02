package com.example.dorstip_app.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dorstip_app.R
import com.example.dorstip_app.Registration.RegistrationActivity
import com.example.dorstip_app.dashboard.MainActivity

// Activity for user login
class LoginActivity : AppCompatActivity() {
    // UI elements
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var loginManager: LoginManager

    // Called when the activity is starting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen) // Set layout for the activity

        // Initialize UI elements
        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        registerTextView = findViewById(R.id.tvRegister)
        loginManager = LoginManager(this)

        // Set click listeners for login button and register text view
        loginButton.setOnClickListener { login() }
        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java)) // Open registration activity
        }

        // Set click listener for skip button to go to main activity
        val btnSkip = findViewById<Button>(R.id.btnSkip)
        btnSkip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Method to handle user login
    private fun login() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Call login method of LoginManager
        loginManager.login(email, password) { success, message ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java)) // Go to main activity on successful login
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show() // Show error message on unsuccessful login
            }
        }
    }
}