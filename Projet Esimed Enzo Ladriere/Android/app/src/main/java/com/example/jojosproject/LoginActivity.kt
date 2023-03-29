package com.example.jojosproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnGoInscription = findViewById<Button>(R.id.BtnGoInscription)

        btnGoInscription.setOnClickListener {
            val intent = Intent(this@LoginActivity,CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}