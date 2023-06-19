package com.example.jojosproject.ViewHolder

import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.R

class ViewHolderJoueur(row: View) : RecyclerView.ViewHolder(row) {
    var progressBar : ProgressBar = row.findViewById(R.id.progressBarJoueur)
    var textView : TextView = row.findViewById(R.id.textViewJoueur)
    var imageButton : ImageButton = row.findViewById(R.id.crossJoueur)
}