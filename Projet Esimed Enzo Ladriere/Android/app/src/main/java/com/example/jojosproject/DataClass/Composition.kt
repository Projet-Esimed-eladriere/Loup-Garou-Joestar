package com.example.jojosproject.DataClass

import com.google.gson.annotations.SerializedName

class Composition:java.io.Serializable {
    @SerializedName("id")
    var id:Int? = null

    @SerializedName("idUtilisateur")
    var idUtilisateur:String? = null

    @SerializedName("nom")
    var nom:String? = null

    @SerializedName("nbrCarte")
    var nbrCarte:Int? = null

    @SerializedName("nbrVillageois")
    var nbrVillageois:Int? = null

    @SerializedName("nbrLoupGarou")
    var nbrLoupGarou:Int? = null

    @SerializedName("petiteFille")
    var petiteFille:Boolean? = null

    @SerializedName("sorciere")
    var sorciere:Boolean? = null

    @SerializedName("chasseur")
    var chasseur:Boolean? = null

    @SerializedName("cupidon")
    var cupidon:Boolean? = null

    @SerializedName("voleur")
    var voleur:Boolean? = null

    @SerializedName("voyante")
    var voyante:Boolean? = null
}