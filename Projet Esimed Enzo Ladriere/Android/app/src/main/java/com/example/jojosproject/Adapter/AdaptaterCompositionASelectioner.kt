package com.example.jojosproject.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.DataClass.Composition
import com.example.jojosproject.R
import com.example.jojosproject.Activity.SelectionDesJoueursActivity
import com.example.jojosproject.ViewHolder.ViewHolderCompositionASelectioner

class AdaptaterCompositionASelectioner(
    private var context: Context,
    private var compositions: ArrayList<Composition>,
    private var recyclerView: RecyclerView
) : RecyclerView.Adapter<ViewHolderCompositionASelectioner>() {

    override fun onBindViewHolder(holder: ViewHolderCompositionASelectioner, position: Int) {
        if(compositions[position] != null) {
            val activity = context as Activity
            activity.runOnUiThread {
                holder.textView.text = compositions[position]?.nom
                holder.imageButton.setOnClickListener {
                    val intent = Intent(context, SelectionDesJoueursActivity::class.java)
                    intent.putExtra("CompositionDeLaPartie", compositions[position])
                    activity.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolderCompositionASelectioner {
        return ViewHolderCompositionASelectioner(
            LayoutInflater.from(context)
                .inflate(R.layout.composition_a_selectioner, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return compositions.size
    }
}