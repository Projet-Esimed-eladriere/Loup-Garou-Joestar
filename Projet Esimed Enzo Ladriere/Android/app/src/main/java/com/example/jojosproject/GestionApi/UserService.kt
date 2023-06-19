package com.example.jojosproject.GestionApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    private val apiUrl = "http://192.168.174.190:3000"
    private val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userAccountRoutes: UserRoutes = retrofit.create(UserRoutes::class.java)
}