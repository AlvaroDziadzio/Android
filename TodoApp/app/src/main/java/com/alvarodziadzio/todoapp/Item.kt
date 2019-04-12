package com.alvarodziadzio.todoapp

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    var complete: Boolean
)