package com.example.jojosproject.ViewHolder

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.R

class ViewHolderCompositionASelectioner(row: View) : RecyclerView.ViewHolder(row) {
    var textView : TextView = row.findViewById(R.id.textViewCompositionASelectioner)
    var imageButton : ImageButton = row.findViewById(R.id.checkComposition)
}