package com.alvarodziadzio.trivia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            Log.d("Pass", password.text.toString())
            HttpWorkbench.auth(email.text.toString(), password.text.toString()) {
                if (it != null) {
                    if (it.getBoolean("sucesso")) {
                        val pref = PreferenceManager.getDefaultSharedPreferences(this)
                        pref.edit().putString("email", email.text.toString()).apply()
                        pref.edit().putString("password", password.text.toString()).apply()
                        pref.edit().apply()
                        Log.d("Pass", "success")

                        val email = pref.getString("email", "a")
                        val password = pref.getString("password", "a")

                        Log.d("email on prefs", email)
                        Log.d("password on prefs", password)

                    }
                    else {
                        error.text = it.getString("mensagem")
                    }
                }
            }
        }

        registrer.setOnClickListener {
            if (name.text.isBlank()) {
                error.text = "insira um nome"
            }
            else {
                HttpWorkbench.register(email.toString(), password.toString(), name.toString()) {
                    if (it != null) {
                        if (it.getBoolean("sucesso")) {
                            val pref = PreferenceManager.getDefaultSharedPreferences(this)
                            pref.edit().putString("email", email.text.toString()).apply()
                            pref.edit().putString("password", password.text.toString()).apply()
                        }
                        else {
                            error.text = it.getString("mensagem")
                        }
                    }
                }
            }


        }

    }
}
