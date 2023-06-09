package com.example.jojosproject

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    override fun onResume() {
        super.onResume()
        val jwtToken = JWTToken.getInstance(this)
        val myService = UserService()
        val context = this@LoginActivity
        val pseudo = context.getSharedPreferences(
            "pseudonyme",
            Context.MODE_PRIVATE
        ).getString("pseudonyme", null)

        if (jwtToken.isValid()) {
            myService.userAccountRoutes.getUtilisateurParPseudonyme(jwtToken.token!!, pseudo)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.code() == 200) {
                            context.getSharedPreferences("idUtilisateur", Context.MODE_PRIVATE)
                                .edit().putString("idUtilisateur", response.body()!!.id).apply()
                            startActivity(Intent(context, MenuActivity::class.java))
                        } else {
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                    }
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initialisation */
        supportActionBar?.setDisplayShowTitleEnabled(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_login)

        val btnGoInscription = findViewById<Button>(R.id.BtnGoInscription)
        val btnConnexion = findViewById<Button>(R.id.BtnSeConnecter)

        val progressBar = findViewById<ProgressBar>(R.id.progressBarMain)
        val jwtToken = JWTToken.getInstance(this)
        val myService = UserService()



        btnConnexion.setOnClickListener {
            val builder = AlertDialog.Builder(this@LoginActivity)
            var pseudonyme = findViewById<EditText>(R.id.pseudonymeLogin).text
            var motDePasse = findViewById<EditText>(R.id.motDePasseLogin).text
            val loginData = UserRoutes.LoginData(pseudonyme.toString(), motDePasse.toString())
            progressBar.visibility = View.VISIBLE
            myService.userAccountRoutes.log(loginData)
                .enqueue(object : Callback<AuthenticationResult> {

                    override fun onResponse(
                        call: Call<AuthenticationResult>,
                        responseLogin: Response<AuthenticationResult>) {
                        if (responseLogin.code() == 200 && responseLogin.body()?.token != null) {
                            jwtToken.changeToken(responseLogin.body()!!.token!!)
                            myService.userAccountRoutes.getUtilisateurParPseudonyme(
                                jwtToken.token!!,
                                pseudonyme.toString()
                            )
                                .enqueue(object : Callback<User> {
                                    override fun onResponse(
                                        call: Call<User>,
                                        responseUser: Response<User>
                                    ) {
                                        if (responseUser.code() == 200) {
                                            this@LoginActivity.getSharedPreferences(
                                                "idUtilisateur",
                                                Context.MODE_PRIVATE
                                            )
                                                .edit()
                                                .putString(
                                                    "idUtilisateur",
                                                    responseUser.body()!!.id
                                                )
                                                .apply()
                                            this@LoginActivity.getSharedPreferences(
                                                "pseudonyme",
                                                Context.MODE_PRIVATE
                                            )
                                                .edit()
                                                .putString("pseudonyme", pseudonyme.toString())
                                                .apply()
                                            val intent =
                                                Intent(this@LoginActivity, MenuActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }

                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                        builder.setMessage("Erreur Web !")
                                        builder.create().show()
                                        progressBar.visibility = View.INVISIBLE
                                        Log.e("onFailure", "Error: ${t.message}")
                                    }
                                })
                        } else {
                            //myTools.displayError(this@loginActivity, response.code())
                        }
                        progressBar.visibility = View.INVISIBLE
                    }

                    override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {
                        builder.setMessage("Erreur Web !")
                        builder.create().show()
                        progressBar.visibility = View.INVISIBLE
                        Log.e("onFailure", "Error: ${t.message}")
                    }
                })
        }

        btnGoInscription.setOnClickListener {
            val intent = Intent(this@LoginActivity, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}