package com.example.jojosproject

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolderCompositionASelectioner(row: View) : RecyclerView.ViewHolder(row) {
    var textView : TextView = row.findViewById(R.id.textViewCompositionASelectioner)
    var imageButton : TextView = row.findViewById(R.id.checkComposition)
}