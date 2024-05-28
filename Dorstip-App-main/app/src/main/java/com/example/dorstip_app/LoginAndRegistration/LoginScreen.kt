package com.example.dorstip_app.LoginAndRegistration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.dorstip_app.R
import com.example.dorstip_app.dashboard.MainActivity

class LoginScreen : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        registerTextView = findViewById(R.id.tvRegister)
        queue = Volley.newRequestQueue(this.applicationContext)

        loginButton.setOnClickListener { login() }
        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegistrationScreen::class.java))
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
        val url = "https://hetwapen.projects.adainforma.tk/api/v1/login?email=$email&password=$password"

        val request = StringRequest(Request.Method.GET, url,
            { response ->
                if (response.contains("success")) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Invalid login credentials", Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                Toast.makeText(this, "Login failed: ${error.message}", Toast.LENGTH_LONG).show()
            })

        queue.add(request)
    }
}