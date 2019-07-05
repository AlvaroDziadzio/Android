package com.alvarodziadzio.trivia.questionProvider

import android.text.Html
import android.util.Log
import com.alvarodziadzio.trivia.HttpWorkbench
import com.alvarodziadzio.trivia.data.Question
import org.json.JSONObject

class QuestionProvider(ready: () -> Unit) {

    private val queue: MutableList<Question> = mutableListOf()

    var filter: QuestionFilter = QuestionFilter(10)
    set(q) {
        field = q
        reset()
    }

    init {

        HttpWorkbench.getQuestions(filter) {
            if (it != null) {

                val arr = it.getJSONArray("results")

                for (i in 0 until arr.length()) {
                    queue.add(jsonToQuestion(arr[i] as JSONObject))
                }

                ready()

            }
        }
    }

    fun next(): Question? {

        if (queue.size > 5)
            load()

        return if (queue.size > 0) queue.removeAt(0) else null

    }

    private fun reset() {

        HttpWorkbench.getQuestions(filter) {
            if (it != null) {

                val arr = it.getJSONArray("results")

                Log.d("Reset", "questions reseted")

                queue.clear()
                for (i in 0 until arr.length()) {
                    queue.add(jsonToQuestion(arr[i] as JSONObject))
                }

            }
        }

    }

    private fun load() {

        HttpWorkbench.getQuestions(filter) {
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
        val question = Html.fromHtml(obj.getString("question")).toString()
        val correct = Html.fromHtml(obj.getString("correct_answer")).toString()
        val incorrect: MutableList<String> = mutableListOf()

        val arr = obj.getJSONArray("incorrect_answers")
        for (i in 0 until arr.length()) {
            incorrect.add(Html.fromHtml(arr.getString(i)).toString())
        }

        return Question(cat, type, difficulty, question, correct, incorrect.toList())

    }


}