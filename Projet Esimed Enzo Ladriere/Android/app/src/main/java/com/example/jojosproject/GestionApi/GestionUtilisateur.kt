package com.example.jojosproject.GestionApi

class GestionUtilisateur {

    fun verifiCationSaisie(pseudonyme: String, nom: String,
                            prenom: String, password: String, confirmationPassword: String) : Boolean
    {
        return (estLesChampsRemplit(pseudonyme, nom, prenom)
                && verificationMotDePasse(password, confirmationPassword))
    }

    fun estLesChampsRemplit(pseudonyme: String, nom: String,
                           prenom: String) : Boolean
    {
        return pseudonyme != "" && nom != "" && prenom != ""
    }

    fun verificationMotDePasse(password: String, confirmationPassword: String) : Boolean
    {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}\$")
        if(password.matches(regex))
        {
            if(password == confirmationPassword)
            {
                return true
            }
            return false
        }
        return false
    }
}