package com.alvarodziadzio.trivia.data

data class User(val email: String,
                val password: String,
                var points: Int)