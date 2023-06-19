package com.example.jojosproject.Activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.Adapter.AdaptaterJoueurASelectioner
import com.example.jojosproject.DataClass.Composition
import com.example.jojosproject.DataClass.Joueur
import com.example.jojosproject.GestionApi.JWTToken
import com.example.jojosproject.R
import com.example.jojosproject.GestionApi.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectionDesJoueursActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_selection_des_joueurs)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSelectionJoueur)
        val myService = UserService()
        val jwtToken = JWTToken.getInstance(this)
        val idUtilisateur =
            this@SelectionDesJoueursActivity.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
                .getString("idUtilisateur", null)
        val joueursChoisis = ArrayList<Joueur>()
        val buttonValiderJoueur = findViewById<Button>(R.id.buttonValiderJoueur)
        val intent = intent
        val compositionDeLaPartie = intent.getSerializableExtra("CompositionDeLaPartie") as? Composition


        myService.userAccountRoutes.getJoueur(jwtToken.token, idUtilisateur)
            .enqueue(object : Callback<ArrayList<Joueur>> {
                override fun onResponse(
                    call: Call<ArrayList<Joueur>>,
                    response: Response<ArrayList<Joueur>>
                ) {
                    if (response.code() == 200) {
                        runOnUiThread {
                            val adaptaterSelectionJoueur =
                                AdaptaterJoueurASelectioner(
                                    this@SelectionDesJoueursActivity,
                                    response.body()!!,
                                    recyclerView,
                                    joueursChoisis
                                )
                            recyclerView.layoutManager =
                                LinearLayoutManager(this@SelectionDesJoueursActivity)
                            recyclerView.adapter = adaptaterSelectionJoueur
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Joueur>>, t: Throwable) {
                }
            })

        buttonValiderJoueur.setOnClickListener {
            val nbrCarte = compositionDeLaPartie?.nbrCarte
            val calcul = nbrCarte!! - joueursChoisis.size

            val message = when {
                calcul < 0 -> "Il faut enlever ${calcul * -1} joueur(s)"
                calcul > 0 -> "Il manque $calcul joueur(s)"
                else -> {
                    val intent = Intent(this, PartieActivity::class.java)
                    intent.putExtra("Composition", compositionDeLaPartie)
                    intent.putExtra("Joueurs", joueursChoisis)
                    startActivity(intent)
                    return@setOnClickListener
                }
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
