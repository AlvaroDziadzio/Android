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


    // Return the url formatted string
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