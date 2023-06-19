package com.example.jojosproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jojosproject.Adapter.AdaptaterJoueurPartie
import com.example.jojosproject.DataClass.Carte
import com.example.jojosproject.DataClass.Composition
import com.example.jojosproject.DataClass.Joueur
import com.example.jojosproject.R

class PartieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partie)

        val intent = intent
        val compositionDeLaPartie = intent.getSerializableExtra("Composition") as Composition
        val joueursChoisis = intent.getSerializableExtra("Joueurs") as List<Joueur>
        val joueurCarteMap = CarteUtils.attribuerCartesAleatoirement(compositionDeLaPartie, joueursChoisis)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPartie)
        val adapter = AdaptaterJoueurPartie(joueurCarteMap)

        // Configurer le RecyclerView avec l'adaptateur
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}

object CarteUtils {
    fun attribuerCartesAleatoirement(composition: Composition, joueurs: List<Joueur>): Map<Joueur, Carte> {
        val cartesDisponibles = mutableListOf<Carte>()

        repeat(composition.nbrVillageois ?: 0) {
            cartesDisponibles.add(Carte("Villageois"))
        }
        repeat(composition.nbrLoupGarou ?: 0) {
            cartesDisponibles.add(Carte("LoupGarou"))
        }
        if (composition.petiteFille == true) {
            cartesDisponibles.add(Carte("PetiteFille"))
        }
        if (composition.sorciere == true) {
            cartesDisponibles.add(Carte("Sorciere"))
        }
        if (composition.chasseur == true) {
            cartesDisponibles.add(Carte("Chasseur"))
        }
        if (composition.cupidon == true) {
            cartesDisponibles.add(Carte("Cupidon"))
        }
        if (composition.voleur == true) {
            cartesDisponibles.add(Carte("Voleur"))
        }
        if (composition.voyante == true) {
            cartesDisponibles.add(Carte("Voyante"))
        }

        cartesDisponibles.shuffle()

        val joueurCarteMap = mutableMapOf<Joueur, Carte>()

        joueurs.forEach { joueur ->
            if (cartesDisponibles.isEmpty()) {
                throw IllegalArgumentException("Il n'y a pas suffisamment de cartes disponibles pour tous les joueurs.")
            }
            val carte = cartesDisponibles.removeAt(0)
            joueurCarteMap[joueur] = carte
        }

        return joueurCarteMap
    }
}
