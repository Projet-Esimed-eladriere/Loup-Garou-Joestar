package com.example.jojosproject.Activity

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.jojosproject.GestionApi.GestionUtilisateur
import com.example.jojosproject.R
import com.example.jojosproject.DataClass.User
import com.example.jojosproject.GestionApi.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initialisation */
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        /* Initialisation */
        setContentView(R.layout.activity_create_account)

        val mygestionUtilisateur = GestionUtilisateur()
        val myService = UserService()
        val btnCreerCompte = findViewById<Button>(R.id.BtnCreerCompte)
        val btnRetour = findViewById<Button>(R.id.BtnRetour)

        btnCreerCompte.setOnClickListener {
            val builder = AlertDialog.Builder(this@CreateAccountActivity)
            val pseudonyme = findViewById<EditText>(R.id.pseudonymeCreateAccount).text.toString()
            val nom = findViewById<EditText>(R.id.lastnameCreateAccount).text.toString()
            val prenom = findViewById<EditText>(R.id.nameCreateAccount).text.toString()
            val motDePasse = findViewById<EditText>(R.id.passwordCreateAccount).text.toString()
            val confirmationMotDePasse = findViewById<EditText>(R.id.confirmationPasswordCreateAccount).text.toString()
            if(mygestionUtilisateur.
                verifiCationSaisie(pseudonyme,nom,prenom,motDePasse,confirmationMotDePasse))
            {
                val userToAdd = User()
                userToAdd.pseudonyme = pseudonyme
                userToAdd.nom = nom
                userToAdd.prenom = prenom
                userToAdd.motDePasse = motDePasse
                myService.userAccountRoutes.postUsers(userToAdd).enqueue(object : Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if(response.code() == 200)
                        {
                            val alertDialogDeleteCategory = AlertDialog.Builder(this@CreateAccountActivity)
                            alertDialogDeleteCategory.setTitle("Compte créer : ${response.body()!!.pseudonyme}")
                            alertDialogDeleteCategory.setPositiveButton("Ok"){ _, _ ->
                                val intent = Intent(this@CreateAccountActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            alertDialogDeleteCategory.create().show()
                        }
                        else if(response.code() == 400)
                        {
                            builder.setMessage("Pseudonyme déja utilisé")
                            builder.create().show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        builder.setMessage("Error Web !")
                        builder.create().show()
                    }
                })
            }
            else
            {
                builder.setMessage("Veuillez remplir correctement le formulaire :\n" +
                        "- Completer tout les champs\n" +
                        "- Le mot de passe doit contenir au moin " +
                        "(une miniscule, une majuscule, un chiffre, et 8 charactere)")
                builder.create().show()
            }
        }

        btnRetour.setOnClickListener {
            val intent = Intent(this@CreateAccountActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}