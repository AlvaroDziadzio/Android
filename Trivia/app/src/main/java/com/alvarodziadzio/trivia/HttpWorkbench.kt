package com.alvarodziadzio.trivia

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

data class HttpRequest(var url: String) {
    var params: Map<String, String> = mapOf()
    var method: String = "GET"
}

class HttpRequestTask(private val request: HttpRequest, val onResult: (JSONObject?) -> Unit): AsyncTask<Unit, Unit, JSONObject>() {

    override fun doInBackground(vararg params: Unit?): JSONObject? {

        // Make the URL
        val url = URL(request.url + "?" + buildParamString(request.params))
        val con = url.openConnection() as HttpsURLConnection

        // Configure Request
        con.requestMethod = request.method
        con.setRequestProperty("Accept", "application/json")

        // Verify Response
        if (con.responseCode == 200) {

            val buffReader = BufferedReader(InputStreamReader(con.inputStream))
            val sb = StringBuffer()

            do {
                val str = buffReader.readLine()
                sb.append(str)
            } while (str != null)

            buffReader.close()
            return JSONObject(sb.toString())

        }
        else {
            Log.e("Https", "failed to retrieve $url -> ${con.responseCode}")
        }

        return null

    }


    // Callback
    override fun onPostExecute(result: JSONObject?) {
        super.onPostExecute(result)
        onResult(result)
    }


    // Return the formatted url string
    private fun buildParamString(params: Map<String, String>): String {

        val sb = StringBuffer()

        for (i in params) {
            sb.append(URLEncoder.encode(i.key, "UTF-8"))
            sb.append("=")
            sb.append(URLEncoder.encode(i.value, "UTF-8"))
            sb.append("&")
        }

        return sb.toString()

    }

}

object HttpWorkbench {

    // List of possible requests
    val reqLogin    = HttpRequest("https://tads2019-todo-list.herokuapp.com/usuario/login").apply { method = "POST" }
    val reqRegister = HttpRequest("https://tads2019-todo-list.herokuapp.com/usuario/registrar").apply { method = "POST" }
    val reqPoints   = HttpRequest("https://tads2019-todo-list.herokuapp.com/usuario/pontuacao").apply { method = "PUT" }
    val reqRanking  = HttpRequest("https://tads2019-todo-list.herokuapp.com/ranking")
    val reqQuestion = HttpRequest("https://opentdb.com/api.php")

    fun getQuestions(params: QuestionProvider.QuestionProviderConfig, callback: (JSONObject?) -> Unit) {

        val map = mutableMapOf("amount" to params.amount.toString())

        if (params.category != null)
            map["category"] = params.category!!

        if (params.difficulty != null)
            map["difficulty"] = params.difficulty!!

        if (params.type != null)
            map["type"] = params.type!!

        reqQuestion.params = map
        HttpRequestTask(reqQuestion, callback).execute()
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