package com.alvarodziadzio.todoapp

data class Item(
    var id: Int,
    var title: String,
    var description: String,
    var isComplete: Boolean
)