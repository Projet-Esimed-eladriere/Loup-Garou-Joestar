package com.example.jojosproject

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface UserRoutes {
    // Users
    @GET("/users/{pseudonyme}")
    fun getUtilisateurParPseudonyme(
        @Header("Authorization") token:String,
        @Path("pseudonyme") pseudonyme: String?): Call<User>

    @POST("/users")
    fun postUsers(@Body user : User) : Call<User>

    //Login
    data class LoginData(val pseudonyme: String, val motDePasse: String)
    @POST("auth/login")
    fun log(@Body loginData: LoginData): Call<AuthenticationResult>

    //Joueur
    @GET("/joueur/{idUtilisateur}")
    fun getJoueur(
        @Header("Authorization") token:String,
        @Path("idUtilisateur") idUtilisateur:String?): Call<ArrayList<Joueur>>

    @POST("joueur")
    fun createJoueur(
        @Header("Authorization") token: String,
        @Body body: JsonObject) : Call<ArrayList<Joueur>>

    @DELETE("/joueur/{idUtilisateur}/{nom}")
    fun delete(
        @Header("Authorization") token: String,
        @Path("idUtilisateur") idUtilisateur :String?,
        @Path("nom") nom : String?): Call<Any>

    //Composition
    @GET("/composition/{idUtilisateur}")
    fun getComposition(
        @Header("Authorization") token:String,
        @Path("idUtilisateur") idUtilisateur:String?): Call<ArrayList<Composition>>

    @POST("/composition")
    fun createComposition(
        @Header("Authorization") token: String,
        @Body body: Composition) : Call<ArrayList<Composition>>

    @PUT("/composition")
    fun updateComposition(
        @Header("Authorization") token: String,
        @Body body: Composition) : Call<Any>

    @DELETE("composition/{idUtilisateur}/{nom}")
    fun deleteComposition(
        @Header("Authorization") token: String,
        @Path("idUtilisateur") idUtilisateur :String?,
        @Path("nom") nom : String?): Call<Composition>
}