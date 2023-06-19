package com.example.jojosproject.ViewHolder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.R

class ViewHolderJoueurASelectioner(row: View) : RecyclerView.ViewHolder(row) {
    var textView : TextView = row.findViewById(R.id.textViewJoueurASelectioner)
    var checkBox : CheckBox = row.findViewById(R.id.checkJoueurASelectioner)
}