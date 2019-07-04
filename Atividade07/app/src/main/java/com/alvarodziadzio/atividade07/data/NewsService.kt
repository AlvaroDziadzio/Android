package com.alvarodziadzio.atividade07.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService {

    @Headers("Accept: application/json")
    @GET("v2/top-headlines")
    fun listNews(
        @Query("q")
        query: String = "",

        @Query("country")
        country: String = "br",

        @Query("apiKey")
        key: String = "9f1147c25017475dad9662fc80c6eab9"
    ): Call<News>

}