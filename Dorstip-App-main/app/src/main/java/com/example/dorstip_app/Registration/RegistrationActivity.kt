package com.example.dorstip_app.Registration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dorstip_app.Login.LoginActivity
import com.example.dorstip_app.R

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        val firstNameField: EditText = findViewById(R.id.etFirstName)
        val lastNameField: EditText = findViewById(R.id.etLastName)
        val emailField: EditText = findViewById(R.id.etEmail)
        val passwordField: EditText = findViewById(R.id.etPassword)
        val passwordConfirmationField: EditText = findViewById(R.id.etPasswordConfirmation)
        val registerButton: Button = findViewById(R.id.btnRegister)
        val loginField: TextView = findViewById(R.id.tvLogin)

        val firstNameError: TextView = findViewById(R.id.tvFirstNameError)
        val lastNameError: TextView = findViewById(R.id.tvLastNameError)
        val emailError: TextView = findViewById(R.id.tvEmailError)
        val passwordError: TextView = findViewById(R.id.tvPasswordError)
        val passwordConfirmationError: TextView = findViewById(R.id.tvPasswordConfirmationError)

        loginField.setOnClickListener{
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            finish()
        }

        registerButton.setOnClickListener {
            val firstName = firstNameField.text.toString()
            val lastName = lastNameField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val passwordConfirmation = passwordConfirmationField.text.toString()

            // Reset error messages
            firstNameError.visibility = TextView.GONE
            lastNameError.visibility = TextView.GONE
            emailError.visibility = TextView.GONE
            passwordError.visibility = TextView.GONE
            passwordConfirmationError.visibility = TextView.GONE

            val registerUser = RegisterUser(this)
            val validationError = registerUser.validateForm(firstName, lastName, email, password, passwordConfirmation)

            if (validationError != null) {
                when (validationError) {
                    "Please enter a valid first name" -> firstNameError.text = validationError
                    "Please enter a valid last name" -> lastNameError.text = validationError
                    "Please enter a valid email address" -> emailError.text = validationError
                    "Password must be between 4 and 8 characters and include letters and numbers" -> passwordError.text = validationError
                    "Passwords do not match" -> passwordConfirmationError.text = validationError
                    else -> Toast.makeText(this, validationError, Toast.LENGTH_SHORT).show()
                }

                // Show error messages
                if (firstNameError.text.isNotEmpty()) firstNameError.visibility = TextView.VISIBLE
                if (lastNameError.text.isNotEmpty()) lastNameError.visibility = TextView.VISIBLE
                if (emailError.text.isNotEmpty()) emailError.visibility = TextView.VISIBLE
                if (passwordError.text.isNotEmpty()) passwordError.visibility = TextView.VISIBLE
                if (passwordConfirmationError.text.isNotEmpty()) passwordConfirmationError.visibility = TextView.VISIBLE

            } else {
                registerUser.register(firstName, lastName, email, password, passwordConfirmation) {
                    // Navigating to login activity on successful registration
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
