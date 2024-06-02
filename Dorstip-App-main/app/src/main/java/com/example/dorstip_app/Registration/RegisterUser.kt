package com.example.dorstip_app.Registration

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.net.URLEncoder

// Class responsible for registering a new user
class RegisterUser(private val context: Context) {

    // Function to register a new user
    fun register(firstName: String, lastName: String, email: String, password: String, passwordConfirmation: String, onSuccess: () -> Unit) {
        val url = "https://hetwapen.projects.adainforma.tk/api/v1/register"

        // Validate user input
        val validationError = validateForm(firstName, lastName, email, password, passwordConfirmation)
        if (validationError != null) {
            Toast.makeText(context, validationError, Toast.LENGTH_SHORT).show()
            return
        }

        val requestQueue = Volley.newRequestQueue(context)

        // Create a string request for registering the user
        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                // Handle successful registration
                handleSuccess(response, onSuccess)
            },
            Response.ErrorListener { error ->
                // Handle registration error
                val errorMessage = if (error.networkResponse != null) {
                    "Error ${error.networkResponse.statusCode}: ${String(error.networkResponse.data)}"
                } else {
                    "Error: ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("RegisterUser", errorMessage)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            override fun getBody(): ByteArray {
                val params = HashMap<String, String>()
                params["first_name"] = firstName
                params["last_name"] = lastName
                params["email"] = email
                params["password"] = password
                params["password_confirmation"] = passwordConfirmation

                // Encode parameters for POST request
                val postData = StringBuilder()
                for ((key, value) in params) {
                    if (postData.isNotEmpty()) {
                        postData.append("&")
                    }
                    postData.append(URLEncoder.encode(key, "UTF-8"))
                    postData.append("=")
                    postData.append(URLEncoder.encode(value, "UTF-8"))
                }

                Log.d("RegisterUser", "Post Data: $postData") // Log the post data to ensure it's correct
                return postData.toString().toByteArray(Charsets.UTF_8)
            }
        }

        Log.d("RegisterUser", "Sending POST request to $url")
        requestQueue.add(stringRequest)
    }

    // Function to validate user input
    fun validateForm(firstName: String, lastName: String, email: String, password: String, passwordConfirmation: String): String? {
        val onlyLettersPattern = "^[a-zA-Z]+$".toRegex()
        if (firstName.isEmpty() || !firstName.matches(onlyLettersPattern)) {
            return "Please enter a valid first name"
        }

        if (lastName.isEmpty() || !lastName.matches(onlyLettersPattern)) {
            return "Please enter a valid last name"
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Please enter a valid email address"
        }

        val passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{4,8}$".toRegex()
        if (password.isEmpty() || !password.matches(passwordPattern)) {
            return "Password must be between 4 and 8 characters and include letters and numbers"
        }

        if (password != passwordConfirmation) {
            return "Passwords do not match"
        }

        return null
    }

    // Function to handle successful registration
    private fun handleSuccess(response: String, onSuccess: () -> Unit) {
        // Handle success response here
        Log.i("RegisterUser", "User registered successfully: $response")
        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
        onSuccess()
    }
}
