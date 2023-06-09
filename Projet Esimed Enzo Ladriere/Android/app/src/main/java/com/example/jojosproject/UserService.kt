package com.example.jojosproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    private val apiUrl = "http://192.168.0.190:3000"
    private val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userAccountRoutes: UserRoutes = retrofit.create(UserRoutes::class.java)
}