package com.example.jojosproject.Activity

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.Adapter.AdaptaterCompositionASelectioner
import com.example.jojosproject.DataClass.Composition
import com.example.jojosproject.GestionApi.JWTToken
import com.example.jojosproject.R
import com.example.jojosproject.GestionApi.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectionDeCompositionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_de_composition)
        /* Initialisation */
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSelectionComposition)
        val myService = UserService()
        val jwtToken = JWTToken.getInstance(this)
        val idUtilisateur =
            this@SelectionDeCompositionActivity.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
                .getString("idUtilisateur", null)

        myService.userAccountRoutes.getComposition(jwtToken.token,idUtilisateur)
            .enqueue(object : Callback<ArrayList<Composition>>{
                override fun onResponse(
                    call: Call<ArrayList<Composition>>,
                    response: Response<ArrayList<Composition>>
                ) {
                    if(response.code() == 200) {
                        runOnUiThread {
                            val adaptaterSelectionComposition =
                                AdaptaterCompositionASelectioner(
                                    this@SelectionDeCompositionActivity,
                                    response.body()!!,
                                    recyclerView
                                )
                            recyclerView.layoutManager =
                                LinearLayoutManager(this@SelectionDeCompositionActivity)
                            recyclerView.adapter = adaptaterSelectionComposition
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Composition>>, t: Throwable) {
                }

            })
    }
}