package com.example.dorstip_app.Login

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context.applicationContext)
    private val loginUrl = "https://hetwapen.projects.adainforma.tk/api/v1/login"

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(Request.Method.POST, loginUrl, jsonObject,
            { response ->
                if (response.getBoolean("success")) {
                    callback(true, null)
                } else {
                    callback(false, "Invalid login credentials")
                }
            },
            { error ->
                callback(false, "Login failed: ${error.message}")
            })

        queue.add(request)
    }
}