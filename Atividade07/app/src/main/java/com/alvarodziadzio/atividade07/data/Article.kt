package com.alvarodziadzio.atividade07.data

import com.google.gson.annotations.SerializedName

data class Article(

    @SerializedName("author")
    var author: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("description")
    var description: String?,

    @SerializedName("url")
    var url: String?,

    @SerializedName("urlToImage")
    var image: String?,

    @SerializedName("publishedAt")
    var date: String

)