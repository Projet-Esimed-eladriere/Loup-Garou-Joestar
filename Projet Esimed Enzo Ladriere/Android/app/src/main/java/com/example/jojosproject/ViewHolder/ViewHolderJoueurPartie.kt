package com.example.jojosproject.ViewHolder

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.R

class ViewHolderJoueurPartie(row: View) : RecyclerView.ViewHolder(row) {
    var textViewNom : TextView = row.findViewById(R.id.nomJoueurEnPartie)
    var textViewRôle : TextView = row.findViewById(R.id.roleJoueurEnPartie)
    var checkVivant : CheckBox = row.findViewById(R.id.checkJoueurVivant)
    var linearLayoutSpecificite : LinearLayout = row.findViewById(R.id.specificiteRole)
}