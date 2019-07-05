package com.alvarodziadzio.trivia

import com.alvarodziadzio.trivia.data.Question
import kotlin.random.Random

object QuestionFaker {

    val difficulties = listOf("easy", "medium", "hard")
    val types = listOf("multiple", "boolean")
    val categories = listOf("General Knowledge", "Entertainment: Books", "Entertainment: Film", "Science & Nature", "Science: Computers")
    val words = listOf("thing", "banana", "cherry", "car", "lambda", "supreme", "alien", "primary", "nevertheless", "dog", "john", "china", "japan", "london", "true", "false", "either", "do", "the", "was", "is", "then")

    operator fun invoke(): Question {

        // Fake Type
        val type = types.random()
        val correctAnswer: String
        val wrongAnwsers: List<String>
        val category = categories.random()
        val difficulty = difficulties.random()
        val question = StringBuffer()

        // Fake Type
        if (type == "multiple") {
            wrongAnwsers = listOf(words.random(), words.random(), words.random())
            correctAnswer = words.random()
        }
        else {

            if (Random.nextBoolean()) {
                correctAnswer = "true"
                wrongAnwsers = listOf("false")
            }
            else {
                correctAnswer = "false"
                wrongAnwsers = listOf("true")
            }

        }


        // Fake Question
        for (i in 0..Random.nextInt(8, 16)) {
            question.append(words.random())
        }


        return Question(category, type, difficulty, question.toString(), correctAnswer, wrongAnwsers)

    }

}


object QuestionProvider {

    val queue: MutableList<Question> = mutableListOf()

    init {

        for (i in 0..10) {
            queue.add(QuestionFaker())
        }

    }

    fun next() = queue.removeAt(0)
    fun size() = queue.size

}