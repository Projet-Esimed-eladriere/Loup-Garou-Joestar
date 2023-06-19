package com.example.jojosproject.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.jojosproject.DataClass.Composition
import com.example.jojosproject.GestionApi.JWTToken
import com.example.jojosproject.R
import com.example.jojosproject.GestionApi.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        val jwtToken = JWTToken.getInstance(this)
        if(!jwtToken.isValid()) {
            val intent = Intent(this@MenuActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initialisation */
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_menu)

        val jwtToken = JWTToken.getInstance(this)
        val btnJouer = findViewById<Button>(R.id.BtnJouer)
        val btnDeconnexion = findViewById<Button>(R.id.BtnDeconnexion)
        val btnGererJoueur = findViewById<Button>(R.id.BtnGererJoueur)
        val btnGererComposition = findViewById<Button>(R.id.BtnGererComposition)
        val sharedPreferences: SharedPreferences =
            this@MenuActivity.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
        val idUtilisateur = this@MenuActivity.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
            .getString("idUtilisateur", null)
        val myService = UserService()


        btnJouer.setOnClickListener {
                myService.userAccountRoutes.getComposition(jwtToken.token, idUtilisateur)
                    .enqueue(object : Callback<ArrayList<Composition>>{
                        override fun onResponse(
                            call: Call<ArrayList<Composition>>,
                            response: Response<ArrayList<Composition>>
                        ) {
                            if(response.code() == 200) {
                                if(response.body()!!.size != 0) {
                                    val intent = Intent(this@MenuActivity, SelectionDeCompositionActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this@MenuActivity,
                                        "Aucune composistion de créer",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ArrayList<Composition>>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
        }

        btnDeconnexion.setOnClickListener {
            jwtToken.changeToken("")
            sharedPreferences.edit().putString("idUtilisateur", "")
            val intent = Intent(this@MenuActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnGererJoueur.setOnClickListener {
            val intent = Intent(this@MenuActivity, JoueurActivity::class.java)
            startActivity(intent)
        }

        btnGererComposition.setOnClickListener {
            val intent = Intent(this@MenuActivity, MenuCompositionActivity::class.java)
            startActivity(intent)
        }
    }
}