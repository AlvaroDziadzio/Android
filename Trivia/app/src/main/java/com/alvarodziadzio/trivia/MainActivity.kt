package com.alvarodziadzio.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.alvarodziadzio.trivia.data.Question
import com.alvarodziadzio.trivia.data.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = User("alvaro.email@email.com", "123456", 0)
        Game.start(user) {
            populateButtons(Game.nextRemote())
        }


    }

    fun populateButtons(q: Question?) {

        if (q != null) {

            textView.text = q.question

            button.text = q.correct_answer
            button2.text = q.incorrect_answers[0]

            if (q.type == "multiple") {
                button3.text = q.incorrect_answers[1]
                button4.text = q.incorrect_answers[2]
            }

            button.setOnClickListener {
                Game.answer(q.correct_answer)
                populateButtons(Game.nextRemote())
                timeText.text = Game.user.points.toString()
            }

            button2.setOnClickListener {
                Game.answer(q.incorrect_answers[0])
                populateButtons(Game.nextRemote())
            }

            button3.setOnClickListener {
                Game.answer(q.incorrect_answers[0])
                populateButtons(Game.nextRemote())
            }

            button4.setOnClickListener {
                Game.answer(q.incorrect_answers[0])
                populateButtons(Game.nextRemote())
            }

        }

    }


}
