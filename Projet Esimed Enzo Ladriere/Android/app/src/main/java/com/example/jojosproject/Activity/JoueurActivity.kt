package com.example.jojosproject.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.Adapter.AdaptaterJoueur
import com.example.jojosproject.DataClass.Joueur
import com.example.jojosproject.GestionApi.JWTToken
import com.example.jojosproject.R
import com.example.jojosproject.GestionApi.UserService
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoueurActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initialisation */
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_joueur)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val floatingActionButton = findViewById<ImageButton>(R.id.floatingActionButton)
        val floatingRetourButton = findViewById<ImageButton>(R.id.floatingRetourButton)
        val myService = UserService()
        val jwtToken = JWTToken.getInstance(this)
        val idUtilisateur = this@JoueurActivity.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
            .getString("idUtilisateur", null)

        myService.userAccountRoutes.getJoueur(jwtToken.token, idUtilisateur)
            .enqueue(object : Callback<ArrayList<Joueur>> {
            override fun onResponse(call: Call<ArrayList<Joueur>>, response: Response<ArrayList<Joueur>>) {
                if(response.code() == 200) {
                    runOnUiThread {
                        val adaptaterJoueur = AdaptaterJoueur(this@JoueurActivity, response.body()!!, recyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@JoueurActivity)
                        recyclerView.adapter = adaptaterJoueur
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ArrayList<Joueur>>, t: Throwable) {
            }

        })

        floatingActionButton.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.ajouter_joueur)
            dialog.findViewById<Button>(R.id.joueurAnnuler).setOnClickListener {
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.joueurAjouter).setOnClickListener {
                if (idUtilisateur != null) {
                    val jsonJoueur = JsonObject()
                    val nom = dialog.findViewById<TextView>(R.id.nomJoueur).text
                    jsonJoueur.addProperty("idUtilisateur", idUtilisateur)
                    jsonJoueur.addProperty("nom", nom.toString().toUpperCase())
                    myService.userAccountRoutes.createJoueur(jwtToken.token, jsonJoueur)
                        .enqueue(object : Callback<ArrayList<Joueur>> {
                            override fun onResponse(call: Call<ArrayList<Joueur>>, response: Response<ArrayList<Joueur>>) {
                                if(response.code() == 200) {
                                    recyclerView.adapter = AdaptaterJoueur(this@JoueurActivity, response.body()!!, recyclerView)
                                    dialog.dismiss()
                                } else {

                                }
                            }

                            override fun onFailure(call: Call<ArrayList<Joueur>>, t: Throwable) {
                                var err = t
                            }
                        })
                }
            }
            dialog.show()
        }

        floatingRetourButton.setOnClickListener {
            val intent = Intent(this@JoueurActivity, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}