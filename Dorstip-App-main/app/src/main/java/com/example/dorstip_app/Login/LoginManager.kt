package com.example.dorstip_app.Login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context.applicationContext)
    private val loginUrl = "https://hetwapen.projects.adainforma.tk/api/v1/login"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("DorstipAppPrefs", Context.MODE_PRIVATE)

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(Request.Method.POST, loginUrl, jsonObject,
            { response ->
                try {
                    // Log de volledige response voor debuggen
                    Log.d("LoginManager", "Response: $response")

                    if (response.getBoolean("success")) {
                        // Haal eerst het 'data'-object op
                        val data = response.getJSONObject("data")
                        // Haal vervolgens de token uit het 'data'-object
                        val token = data.getString("token")
                        saveToken(token) // Sla de token op
                        callback(true, null)
                    } else {
                        callback(false, "Invalid login credentials")
                    }
                } catch (e: Exception) {
                    // Log parsing errors
                    Log.e("LoginManager", "Error parsing response: ${e.message}", e)
                    callback(false, "Error parsing response: ${e.message}")
                }
            },
            { error ->
                // Log netwerkfouten
                val statusCode = error.networkResponse?.statusCode
                val responseBody = error.networkResponse?.data?.let { String(it) }
                Log.e("LoginManager", "Error: ${error.message}, Status Code: $statusCode, Response: $responseBody")
                callback(false, "Login failed: ${error.message}")
            })

        queue.add(request)
    }



    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
        Log.d("LoginManager", "Token saved: $token")
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove("auth_token").apply()
        Log.d("LoginManager", "Token cleared")
    }

    fun getProfile(callback: (Boolean, JSONObject?) -> Unit) {
        val token = getToken()
        if (token == null) {
            callback(false, null)
            return
        }

        val profileUrl = "https://hetwapen.projects.adainforma.tk/api/v1/me"
        val request = object : JsonObjectRequest(Method.POST, profileUrl, null,
            { response ->
                // Log de volledige serverresponse
                Log.d("ProfileRequest", "Response: $response")
                callback(true, response)
            },
            { error ->
                // Log netwerkfouten en response-body
                val statusCode = error.networkResponse?.statusCode
                val responseBody = error.networkResponse?.data?.let { String(it) }
                Log.e(
                    "ProfileRequest",
                    "Error: ${error.message}, Status Code: $statusCode, Response: $responseBody"
                )
                callback(false, null)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token" // Voeg de token toe aan de headers
                return headers
            }
        }

        queue.add(request)
    }
}
