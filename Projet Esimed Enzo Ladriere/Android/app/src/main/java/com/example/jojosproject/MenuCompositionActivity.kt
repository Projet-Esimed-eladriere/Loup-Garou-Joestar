package com.example.jojosproject

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuCompositionActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        val jwtToken = JWTToken.getInstance(this)
        if (!jwtToken.isValid()) {
            val intent = Intent(this@MenuCompositionActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initialisation */
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_menu_composition)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewComposition)
        val floatingActionButton = findViewById<ImageButton>(R.id.floatingActionButtonMenuCompo)
        val floatingRetourButton = findViewById<ImageButton>(R.id.floatingRetourButtonMenuCompo)
        val myService = UserService()
        val jwtToken = JWTToken.getInstance(this)
        val idUtilisateur =
            this@MenuCompositionActivity.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
                .getString("idUtilisateur", null)

        myService.userAccountRoutes.getComposition(jwtToken.token, idUtilisateur).enqueue(object :
            Callback<ArrayList<Composition>> {
            override fun onResponse(
                call: Call<ArrayList<Composition>>,
                response: Response<ArrayList<Composition>>
            ) {
                if (response.code() == 200) {
                    runOnUiThread {
                        val adaptaterJoueur =
                            AdaptaterComposition(
                                this@MenuCompositionActivity,
                                response.body()!!,
                                recyclerView
                            )
                        recyclerView.layoutManager =
                            LinearLayoutManager(this@MenuCompositionActivity)
                        recyclerView.adapter = adaptaterJoueur
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Composition>>, t: Throwable) {
            }
        })

        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MenuCompositionActivity, EditerCompositionActivity::class.java)
            startActivity(intent)
        }

        floatingRetourButton.setOnClickListener {
            val intent = Intent(this@MenuCompositionActivity, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}