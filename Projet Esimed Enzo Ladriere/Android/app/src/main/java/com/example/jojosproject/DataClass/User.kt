package com.example.jojosproject.DataClass

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id")
    var id:String? = null

    @SerializedName("pseudonyme")
    var pseudonyme:String? = null

    @SerializedName("nom")
    var nom:String? = null

    @SerializedName("prenom")
    var prenom:String? = null

    @SerializedName("motDePasse")
    var motDePasse:String? = null

    override fun toString(): String {
        return "$pseudonyme"
    }

}