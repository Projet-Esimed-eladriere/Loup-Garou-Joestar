package com.example.jojosproject.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.*
import com.example.jojosproject.Activity.EditerCompositionActivity
import com.example.jojosproject.DataClass.Composition
import com.example.jojosproject.GestionApi.JWTToken
import com.example.jojosproject.GestionApi.UserService
import com.example.jojosproject.ViewHolder.ViewHolderComposition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdaptaterComposition(
    private var context: Context,
    private var compositions: ArrayList<Composition>,
    private var recyclerView: RecyclerView
) :
    RecyclerView.Adapter<ViewHolderComposition>() {
    private val jwtToken = JWTToken.getInstance(context)
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
    private val myService = UserService()

    override fun onBindViewHolder(holder: ViewHolderComposition, @SuppressLint("RecyclerView") position: Int) {
        if (compositions[position] != null) {
            val activity = context as Activity
            activity.runOnUiThread {
                holder.progressBar.visibility = View.VISIBLE
                holder.textView.text = compositions[position]?.nom

                holder.imageButtonEdit.setOnClickListener {
                    val intent = Intent(context, EditerCompositionActivity::class.java)
                    intent.putExtra("CompositionAEditer", compositions[position])
                        activity.startActivity(intent)
                }

                holder.imageButtonCross.setOnClickListener {
                    myService.userAccountRoutes.deleteComposition(
                        jwtToken.token,
                        sharedPreferences.getString("idUtilisateur", null),
                        compositions[position]?.nom
                    ).enqueue(object : Callback<Composition> {
                        override fun onResponse(
                            call: Call<Composition>,
                            response: Response<Composition>
                        ) {
                            if (response.code() == 200) {
                                compositions.removeAt(position)
                                recyclerView.adapter =
                                    AdaptaterComposition(context, compositions, recyclerView)
                            }
                        }

                        override fun onFailure(call: Call<Composition>, t: Throwable) {

                        }

                    })
                }
            }
            holder.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComposition {
        return ViewHolderComposition(
            LayoutInflater.from(context).inflate(R.layout.composition, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return compositions.size
    }
}