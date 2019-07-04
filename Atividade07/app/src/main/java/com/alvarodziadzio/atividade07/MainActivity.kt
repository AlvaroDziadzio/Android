package com.alvarodziadzio.atividade07

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alvarodziadzio.atividade07.data.Article
import com.alvarodziadzio.atividade07.data.News
import com.alvarodziadzio.atividade07.data.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: NewsService

    val baseUrl = "https://newsapi.org/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()

        service = retrofit.create(NewsService::class.java)


        service.listNews().enqueue(object : Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("Retrofit", "Failed to load News")
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val n = response.body()
                if (n != null) {
                    Log.d("AAAAAAA", n.articles[0]?.author)
                }
            }


        })


    }
}

// 9f1147c25017475dad9662fc80c6eab9
// API key