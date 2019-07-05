package com.alvarodziadzio.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {

            val req = HttpRequest("https://tads2019-todo-list.herokuapp.com/ranking")
            // req.method = "PUT"
            //req.params = mapOf("email" to editText.text.toString(), "senha" to editText2.text.toString(), "pontos" to "10")

            HttpRequestTask(req) {
                if (it != null) {
                    // textView.text = it.getBoolean("sucesso").toString()
                    Log.d("JSON", it.toString())
                }

            }.execute()

            Log.d("PASS", editText2.text.toString())
            Log.d("EMAIL", editText.text.toString())

        }

    }


}




/*

API de Login -> POST https://tads2019-todo-list.herokuapp.com/usuario/login
email
senha

API de Registro -> POST https://tads2019-todo-list.herokuapp.com/usuario/registrar
email
senha
nome

API para registrar pontuação de jogador -> PUT https://tads2019-todo-list.herokuapp.com/usuario/pontuacao
email
senha
pontos (-10 a 10)

API para consultar o Ranking -> GET https://tads2019-todo-list.herokuapp.com/ranking

 */