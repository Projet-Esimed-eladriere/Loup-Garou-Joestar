package com.example.jojosproject.GestionApi

import android.content.Context
import android.content.SharedPreferences

class JWTToken constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    var token: String = sharedPreferences.getString("token", "") ?: ""

    fun changeToken(value: String) {
        sharedPreferences.edit().putString("token", "Bearer $value").apply()
        token = "Bearer $value"
    }

    fun isValid(): Boolean {
        return !(token == "" || token == "Bearer ")
    }

    companion object {
        @Volatile
        private var INSTANCE: JWTToken? = null

        fun getInstance(context: Context): JWTToken {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: JWTToken(context).also { INSTANCE = it }
            }
        }
    }
}
