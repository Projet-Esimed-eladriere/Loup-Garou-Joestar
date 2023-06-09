package com.example.jojosproject

import com.google.gson.annotations.SerializedName

class Joueur {
    @SerializedName("idUtilisateur")
    var idUtilisateur:String? = null

    @SerializedName("nom")
    var nom:String? = null
}