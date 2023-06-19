package com.example.jojosproject.DataClass

import com.google.gson.annotations.SerializedName

class Joueur:java.io.Serializable {
    @SerializedName("idUtilisateur")
    var idUtilisateur:String? = null

    @SerializedName("nom")
    var nom:String? = null
}