package com.alvarodziadzio.atividade07.data

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("status")
    var status: String?,

    @SerializedName("totalResults")
    var results: Int?,

    @SerializedName("articles")
    var articles: List<Article?>

)