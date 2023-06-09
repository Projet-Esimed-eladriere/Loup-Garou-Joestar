package com.example.jojosproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

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
                    val intent = Intent(context, EditerCompositionActivity::class.java)
                    intent.putExtra("CompositionAEditer", compositions[position])
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