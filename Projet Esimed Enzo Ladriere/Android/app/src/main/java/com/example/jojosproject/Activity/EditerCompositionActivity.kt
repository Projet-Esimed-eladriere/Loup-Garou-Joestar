package com.example.jojosproject.Activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.jojosproject.DataClass.Composition
import com.example.jojosproject.GestionApi.JWTToken
import com.example.jojosproject.R
import com.example.jojosproject.GestionApi.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditerCompositionActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        val jwtToken = JWTToken.getInstance(this)
        if (!jwtToken.isValid()) {
            val intent = Intent(this@EditerCompositionActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initialisation */
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_editer_composition)

        val myService = UserService()
        val jwtToken = JWTToken.getInstance(this)
        val idUtilisateurConnect = this@EditerCompositionActivity.getSharedPreferences(
            "idUtilisateur", Context.MODE_PRIVATE
        ).getString("idUtilisateur", null)

        //Elements
        val nomComposition = findViewById<EditText>(R.id.nomComposition)
        val textViewNbrVillageois = findViewById<TextView>(R.id.NbrVillageois)
        val buttonPlusVillageois = findViewById<Button>(R.id.PlusVillageois)
        val buttonMoinVillageois = findViewById<Button>(R.id.MoinVillageois)
        val textViewNbrLoupGarou = findViewById<TextView>(R.id.NbrLoupGarou)
        val buttonPlusLoupGarou = findViewById<Button>(R.id.PlusLoupGarou)
        val buttonMoinLoupGarou = findViewById<Button>(R.id.MoinLoupGarou)
        val checkboxPetiteFille = findViewById<CheckBox>(R.id.checkboxPetiteFille)
        val checkboxChasseur = findViewById<CheckBox>(R.id.checkboxChasseur)
        val checkboxCupidon = findViewById<CheckBox>(R.id.checkboxCupidon)
        val checkboxSorciere = findViewById<CheckBox>(R.id.checkboxSorciere)
        val checkboxVoleur = findViewById<CheckBox>(R.id.checkboxVoleur)
        val checkboxVoyante = findViewById<CheckBox>(R.id.checkboxVoyante)
        val textViewNombreJoueur = findViewById<TextView>(R.id.NombreJoueur)
        val buttonValiderComposition = findViewById<TextView>(R.id.ValiderComposition)
        val buttonRetourComposition = findViewById<TextView>(R.id.RetourComposition)

        //Objet composition
        var composition = Composition().apply {
            idUtilisateur = idUtilisateurConnect
            nom = ""
            nbrCarte = 0
            nbrVillageois = 0
            nbrLoupGarou = 0
            petiteFille = checkboxPetiteFille.isChecked
            sorciere = checkboxSorciere.isChecked
            chasseur = checkboxChasseur.isChecked
            cupidon = checkboxCupidon.isChecked
            voleur = checkboxVoleur.isChecked
            voyante = checkboxVoyante.isChecked
        }
        //Si editer
        val compositionAEditer = intent.getSerializableExtra("CompositionAEditer")
        if (compositionAEditer != null) {
            composition = compositionAEditer as Composition
            nomComposition.setText(composition.nom)
            nomComposition.isEnabled = false
            textViewNbrVillageois.text = composition.nbrVillageois!!.toString()
            textViewNbrLoupGarou.text = composition.nbrLoupGarou!!.toString()
            checkboxPetiteFille.isChecked = composition.petiteFille!!
            checkboxSorciere.isChecked = composition.sorciere!!
            checkboxChasseur.isChecked = composition.chasseur!!
            checkboxCupidon.isChecked = composition.cupidon!!
            checkboxVoleur.isChecked = composition.voleur!!
            checkboxVoyante.isChecked = composition.voyante!!
            textViewNombreJoueur.text = composition.nbrCarte!!.toString()
        }

        nomComposition.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                composition.nom = nomComposition.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        buttonPlusVillageois.setOnClickListener {
            composition.nbrVillageois = composition.nbrVillageois!! + 1
            composition.nbrCarte = composition.nbrCarte!! + 1
            textViewNbrVillageois.text = composition.nbrVillageois.toString()
            textViewNombreJoueur.text = composition.nbrCarte.toString()
        }

        buttonMoinVillageois.setOnClickListener {
            if (composition.nbrVillageois!! > 0) {
                composition.nbrVillageois = composition.nbrVillageois!! - 1
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNbrVillageois.text = composition.nbrVillageois.toString()
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        buttonPlusLoupGarou.setOnClickListener {
            composition.nbrLoupGarou = composition.nbrLoupGarou!! + 1
            composition.nbrCarte = composition.nbrCarte!! + 1
            textViewNbrLoupGarou.text = composition.nbrLoupGarou.toString()
            textViewNombreJoueur.text = composition.nbrCarte.toString()
        }

        buttonMoinLoupGarou.setOnClickListener {
            if (composition.nbrLoupGarou!! > 0) {
                composition.nbrLoupGarou = composition.nbrLoupGarou!! - 1
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNbrLoupGarou.text = composition.nbrLoupGarou.toString()
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        checkboxPetiteFille.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                composition.petiteFille = true
                composition.nbrCarte = composition.nbrCarte!! + 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            } else {
                composition.petiteFille = false
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        checkboxSorciere.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                composition.sorciere = true
                composition.nbrCarte = composition.nbrCarte!! + 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            } else {
                composition.sorciere = false
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        checkboxChasseur.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                composition.chasseur = true
                composition.nbrCarte = composition.nbrCarte!! + 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            } else {
                composition.chasseur = false
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        checkboxCupidon.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                composition.cupidon = true
                composition.nbrCarte = composition.nbrCarte!! + 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            } else {
                composition.cupidon = false
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        checkboxVoleur.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                composition.voleur = true
                composition.nbrCarte = composition.nbrCarte!! + 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            } else {
                composition.voleur = false
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        checkboxVoyante.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                composition.voyante = true
                composition.nbrCarte = composition.nbrCarte!! + 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            } else {
                composition.voyante = false
                composition.nbrCarte = composition.nbrCarte!! - 1
                textViewNombreJoueur.text = composition.nbrCarte.toString()
            }
        }

        buttonValiderComposition.setOnClickListener {
            composition.nom = nomComposition.text.toString()
            if (composition.nom.isNullOrEmpty() || composition.nbrCarte!! < 3) {
                Toast.makeText(
                    this@EditerCompositionActivity,
                    "Il faut un nom et au moins 3 joueurs",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (compositionAEditer != null) {
                    myService.userAccountRoutes.updateComposition(jwtToken.token, composition)
                        .enqueue(object : Callback<Any> {
                            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                                if (response.code() == 200) {
                                    val intent = Intent(
                                        this@EditerCompositionActivity,
                                        MenuCompositionActivity::class.java
                                    )
                                    startActivity(intent)
                                }
                            }

                            override fun onFailure(call: Call<Any>, t: Throwable) {
                            }
                        })
                } else {
                    myService.userAccountRoutes.createComposition(jwtToken.token, composition)
                        .enqueue(object : Callback<ArrayList<Composition>> {
                            override fun onResponse(
                                call: Call<ArrayList<Composition>>,
                                response: Response<ArrayList<Composition>>
                            ) {
                                if (response.code() == 201) {
                                    val intent = Intent(
                                        this@EditerCompositionActivity,
                                        MenuCompositionActivity::class.java
                                    )
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this@EditerCompositionActivity,
                                        "Nom déja exsitant",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            override fun onFailure(
                                call: Call<ArrayList<Composition>>, t: Throwable
                            ) {
                            }

                        })
                }
            }
        }

        buttonRetourComposition.setOnClickListener {
            val intent = Intent(this@EditerCompositionActivity, MenuCompositionActivity::class.java)
            startActivity(intent)
        }
    }
}