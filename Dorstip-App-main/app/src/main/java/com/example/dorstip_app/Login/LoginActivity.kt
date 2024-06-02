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

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        registerTextView = findViewById(R.id.tvRegister)
        loginManager = LoginManager(this)

        loginButton.setOnClickListener { login() }
        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        val btnSkip = findViewById<Button>(R.id.btnSkip)
        btnSkip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        loginManager.login(email, password) { success, message ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
