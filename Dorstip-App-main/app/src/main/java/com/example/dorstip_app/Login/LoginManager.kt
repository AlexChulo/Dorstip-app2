package com.example.dorstip_app.Login

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

// Manager class for handling user login
class LoginManager(context: Context) {
    // Volley request queue for handling network requests
    private val queue: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    // URL for login endpoint
    private val loginUrl = "https://hetwapen.projects.adainforma.tk/api/v1/login"

    // Method to perform user login
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        // Create parameters for the POST request
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password

        // Convert parameters to JSON object
        val jsonObject = JSONObject(params as Map<*, *>)

        // Create JSON object request for login
        val request = JsonObjectRequest(Request.Method.POST, loginUrl, jsonObject,
            { response ->
                // Check if login is successful based on response
                if (response.getBoolean("success")) {
                    callback(true, null) // Invoke callback with success status and no error message
                } else {
                    callback(false, "Invalid login credentials") // Invoke callback with failure status and error message
                }
            },
            { error ->
                callback(false, "Login failed: ${error.message}") // Invoke callback with failure status and error message
            })

        // Add the request to the request queue
        queue.add(request)
    }
}
