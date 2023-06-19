package com.example.jojosproject.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.DataClass.Joueur
import com.example.jojosproject.R
import com.example.jojosproject.ViewHolder.ViewHolderJoueurASelectioner

class AdaptaterJoueurASelectioner(
    private val context: Context,
    private val joueurs: ArrayList<Joueur>,
    private val recyclerView: RecyclerView,
    private val joueursChoisis: ArrayList<Joueur>
) : RecyclerView.Adapter<ViewHolderJoueurASelectioner>() {

    override fun onBindViewHolder(holder: ViewHolderJoueurASelectioner, position: Int) {
        if (joueurs[position] != null) {
            val activity = context as Activity
            activity.runOnUiThread {
                holder.textView.text = joueurs[position].nom
                holder.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
                    val joueur = joueurs[position]

                    if (isChecked) {
                        joueursChoisis.add(joueur)
                    } else {
                        joueursChoisis.remove(joueur)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderJoueurASelectioner {
        return ViewHolderJoueurASelectioner(
            LayoutInflater.from(context)
                .inflate(R.layout.joueur_a_selectioner, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return joueurs.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
