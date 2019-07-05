package com.alvarodziadzio.trivia

import android.util.Log
import com.alvarodziadzio.trivia.data.Question
import org.json.JSONObject
import kotlin.random.Random

class QuestionProvider {

    val queue: MutableList<Question> = mutableListOf()
    val aLater: MutableList<Question> = mutableListOf()
    val config: QuestionProviderConfig = QuestionProviderConfig(10)

    init {
        loadNew()
    }

    // Dequeue general
    fun nextRemote(): Question {
        if (queue.size < 5)
            loadNew()
        return queue.removeAt(0)
    }

    // Dequeue Answer later
    fun nextLocal(): Question? {
        if (aLater.size > 0)
            return aLater.removeAt(0)
        return null
    }

    // Enqueue
    fun answerLater(q: Question) = aLater.add(q)

    fun reset() {
        queue.clear()
        loadNew()
    }

    private fun loadNew() {
        Log.d("Network", "Fetching new questions!!!")
        HttpWorkbench.getQuestions(config) {
            if (it != null) {

                val arr = it.getJSONArray("results")

                for (i in 0 until arr.length()) {
                    queue.add(jsonToQuestion(arr[i] as JSONObject))
                }

            }
        }
    }

    private fun jsonToQuestion(obj: JSONObject): Question {

        val cat = obj.getString("category")
        val type = obj.getString("type")
        val difficulty = obj.getString("difficulty")
        val question = obj.getString("question")
        val correct = obj.getString("correct_answer")
        val incorrect: MutableList<String> = mutableListOf()

        val arr = obj.getJSONArray("incorrect_answers")
        for (i in 0 until arr.length()) {
            incorrect.add(arr.getString(i))
        }

        return Question(cat, type, difficulty, question, correct, incorrect.toList())

    }

    data class QuestionProviderConfig(var amount: Int) {
        var category: String? = null
        var difficulty: String? = null
        var type: String? = null
    }

}