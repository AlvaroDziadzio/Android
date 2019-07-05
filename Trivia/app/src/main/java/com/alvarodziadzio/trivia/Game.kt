package com.alvarodziadzio.trivia

import android.util.Log
import com.alvarodziadzio.trivia.data.Question
import com.alvarodziadzio.trivia.data.User
import com.alvarodziadzio.trivia.questionProvider.QuestionProvider

object Game {

    lateinit var user: User
        private set

    var currentQuestion: Question? = null
    var isLater = false
    var time: Long = 50000
    var lastTime: Long = 0

    private lateinit var provider: QuestionProvider

    private val later: MutableList<Question> = mutableListOf()

    fun start(_user: User, ready: () -> Unit) {
        user = _user
        provider = QuestionProvider(ready)
        lastTime = System.currentTimeMillis()
        calcTime()
    }

    fun answer(answer: String) {

        if (!timeout()) {
            var point = currentQuestionPoints()

            if (answer != currentQuestion!!.correct_answer)
                point *= -1

            HttpWorkbench.addPoints(user, point) {
                Log.d("POINTS", it!!.getString("mensagem"))
            }
        }

    }

    fun answerLater() {

        if (!timeout()) {
            HttpWorkbench.addPoints(user, -(currentQuestionPoints()-2)) {
                Log.d("POINTS", it!!.getString("mensagem"))
            }
            if (currentQuestion != null)
                later.add(currentQuestion!!)
        }

    }

    fun hasLater() = later.size > 0


    private fun currentQuestionPoints(): Int {

        return if (isLater) {
            when (currentQuestion?.difficulty) {
                "hard" -> 8
                "medium" -> 6
                "easy" -> 4
                else -> 0
            }
        }
        else {
            when (currentQuestion?.difficulty) {
                "hard" -> 10
                "medium" -> 8
                "easy" -> 5
                else -> 0
            }
        }

    }

    private fun timeout(): Boolean {
        val timeout = System.currentTimeMillis() > (lastTime + time)
        if (timeout) {
            HttpWorkbench.addPoints(user, -currentQuestionPoints()) {
                Log.d("POINTS", "TIMEOUT")
            }
        }
        lastTime = System.currentTimeMillis()
        return timeout
    }

    private fun calcTime() {
        time = when (currentQuestion?.difficulty) {
            "hard" -> 15000
            "medium" -> 30000
            "easy" -> 45000
            else -> 50000
        }
    }

    fun nextRemote(): Question? {
        currentQuestion = provider.next()
        isLater = false
        timeout()
        return currentQuestion
    }

    fun nextLater(): Question? {
        currentQuestion = if (later.size > 0) later.removeAt(0) else null
        isLater = true
        timeout()
        return currentQuestion
    }

}