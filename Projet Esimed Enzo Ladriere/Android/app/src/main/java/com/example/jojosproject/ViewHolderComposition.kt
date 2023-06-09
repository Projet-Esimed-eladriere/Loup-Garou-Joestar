package com.example.jojosproject

import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolderComposition(row: View) : RecyclerView.ViewHolder(row) {
    var progressBar : ProgressBar = row.findViewById(R.id.progressBarComposition)
    var textView : TextView = row.findViewById(R.id.textViewComposition)
    var imageButtonEdit : ImageButton = row.findViewById(R.id.editComposition)
    var imageButtonCross : ImageButton = row.findViewById(R.id.crossComposition)
}