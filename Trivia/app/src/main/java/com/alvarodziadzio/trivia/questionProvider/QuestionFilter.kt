package com.alvarodziadzio.trivia.questionProvider

data class QuestionFilter(var amount: Int) {
    var category: String? = null
    var difficulty: String? = null
    var type: String? = null
}