package com.alvarodziadzio.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val s = QuestionProvider()
        s.config.difficulty = "hard"
        s.reset()

        button.setOnClickListener {

            Log.d("QUESTION", s.nextRemote().toString())

        }

    }


}
