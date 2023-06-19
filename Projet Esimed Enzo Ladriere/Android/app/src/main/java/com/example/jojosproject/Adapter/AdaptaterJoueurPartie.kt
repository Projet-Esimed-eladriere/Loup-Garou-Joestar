package com.example.jojosproject.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.DataClass.Carte
import com.example.jojosproject.DataClass.Joueur
import com.example.jojosproject.R
import com.example.jojosproject.ViewHolder.ViewHolderJoueurPartie

class AdaptaterJoueurPartie (private val joueursEtCartes: Map<Joueur, Carte>) :
    RecyclerView.Adapter<ViewHolderJoueurPartie>() {
    override fun onBindViewHolder(holder: ViewHolderJoueurPartie, position: Int) {
        val (joueur, carte) = joueursEtCartes.entries.elementAt(position)

        // Mettez à jour les vues du ViewHolder avec les données du joueur et de la carte
        holder.textViewNom.text = joueur.nom
        holder.textViewRôle.text = carte.nom
        holder.checkVivant.isChecked = false

        // Afficher/masquer et configurer les vues spécifiques en fonction du nom de la carte
        when (carte.nom) {
                "Sorciere" -> {
                    holder.linearLayoutSpecificite.removeAllViews() // Supprimer les vues existantes

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    // Créer et ajouter le TextView "Vie"
                    val textViewVie = TextView(holder.linearLayoutSpecificite.context)
                    textViewVie.text = "Vie"
                    textViewVie.layoutParams = layoutParams
                    textViewVie.setTextColor(Color.WHITE) // Changer la couleur du texte en blanc
                    textViewVie.setBackgroundColor(Color.TRANSPARENT) // Définir un fond transparent
                    holder.linearLayoutSpecificite.addView(textViewVie)

                    // Créer et ajouter la CheckBox "Vie"
                    val checkBoxVie = CheckBox(holder.linearLayoutSpecificite.context)
                    checkBoxVie.layoutParams = layoutParams
                    checkBoxVie.isChecked = true
                    holder.linearLayoutSpecificite.addView(checkBoxVie)

                    // Créer et ajouter le TextView "Mort"
                    val textViewMort = TextView(holder.linearLayoutSpecificite.context)
                    textViewMort.text = "Mort"
                    textViewMort.layoutParams = layoutParams
                    textViewMort.setTextColor(Color.WHITE) // Changer la couleur du texte en blanc
                    textViewMort.setBackgroundColor(Color.TRANSPARENT) // Définir un fond transparent
                    holder.linearLayoutSpecificite.addView(textViewMort)

                    // Créer et ajouter la CheckBox "Mort"
                    val checkBoxMort = CheckBox(holder.linearLayoutSpecificite.context)
                    checkBoxMort.layoutParams = layoutParams
                    checkBoxMort.isChecked = true
                    holder.linearLayoutSpecificite.addView(checkBoxMort)
                }

            "Cupidon" -> {
                holder.linearLayoutSpecificite.removeAllViews() // Supprimer les vues existantes

                // Créer et ajouter les Spinners dynamiquement
                val spinnerJoueur1 = Spinner(holder.linearLayoutSpecificite.context)
                // Récupérer la liste des joueurs disponibles
                val listeJoueursDisponibles = joueursEtCartes.keys.map { it.nom }
                // Configurer les options du spinner en fonction des joueurs disponibles
                val joueurAdapter1 = ArrayAdapter(
                    holder.linearLayoutSpecificite.context,
                    android.R.layout.simple_spinner_item,
                    listeJoueursDisponibles
                )
                spinnerJoueur1.adapter = joueurAdapter1
                spinnerJoueur1.setBackgroundColor(Color.WHITE) // Changer la couleur du fond en blanc
                holder.linearLayoutSpecificite.addView(spinnerJoueur1)

                // Créer et ajouter le cœur
                val heartTextView = TextView(holder.linearLayoutSpecificite.context)
                heartTextView.text = "❤️"
                holder.linearLayoutSpecificite.addView(heartTextView)

                val spinnerJoueur2 = Spinner(holder.linearLayoutSpecificite.context)
                // Configurer les options du spinner en fonction des joueurs disponibles
                val joueurAdapter2 = ArrayAdapter(
                    holder.linearLayoutSpecificite.context,
                    android.R.layout.simple_spinner_item,
                    listeJoueursDisponibles
                )
                spinnerJoueur2.adapter = joueurAdapter2
                spinnerJoueur2.setBackgroundColor(Color.WHITE) // Changer la couleur du fond en blanc
                holder.linearLayoutSpecificite.addView(spinnerJoueur2)
            }
            else -> {
                // Pour les autres cartes, masquer le LinearLayout
                holder.linearLayoutSpecificite.visibility = View.INVISIBLE
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderJoueurPartie {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.joueur_en_partie, parent, false)
        return ViewHolderJoueurPartie(view)
    }

    override fun getItemCount(): Int {
        return joueursEtCartes.size
    }
}