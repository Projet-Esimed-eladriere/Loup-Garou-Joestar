package com.example.jojosproject.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.DataClass.Joueur
import com.example.jojosproject.GestionApi.JWTToken
import com.example.jojosproject.R
import com.example.jojosproject.GestionApi.UserService
import com.example.jojosproject.ViewHolder.ViewHolderJoueur
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdaptaterJoueur(private var context: Context, private var joueurs: ArrayList<Joueur>,
                      private var recyclerView: RecyclerView) :
    RecyclerView.Adapter<ViewHolderJoueur>() {
    private val jwtToken = JWTToken.getInstance(context)
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
    private val myService = UserService()

    override fun onBindViewHolder(holder: ViewHolderJoueur, @SuppressLint("RecyclerView") position: Int) {
        if (joueurs[position] != null) {
            val activity = context as Activity
            activity.runOnUiThread {
                holder.progressBar.visibility = View.VISIBLE
                holder.textView.text = joueurs[position]?.nom
                holder.imageButton.setOnClickListener {
                    myService.userAccountRoutes.delete(
                        jwtToken.token,
                        sharedPreferences.getString("idUtilisateur", null),
                        joueurs[position]?.nom
                    ).enqueue(object : Callback<Any> {
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            if (response.code() == 200) {
                                joueurs.removeAt(position)
                                recyclerView.adapter = AdaptaterJoueur(context, joueurs,recyclerView)
                            } else {
                                //Erreur
                            }
                        }

                        override fun onFailure(call: Call<Any>, t: Throwable) {
                        }
                    })
                }
                holder.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderJoueur {
        return ViewHolderJoueur(
            LayoutInflater.from(context).inflate(R.layout.joueur, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return joueurs.size
    }
}