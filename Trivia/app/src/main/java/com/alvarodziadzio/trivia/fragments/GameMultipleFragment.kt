package com.alvarodziadzio.trivia.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvarodziadzio.trivia.R
import com.alvarodziadzio.trivia.data.Question
import kotlinx.android.synthetic.main.fragment_game_multiple.*

@SuppressLint("ValidFragment")
class GameMultipleFragment(val q: Question): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_multiple, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        question.text = q.question
        button.text = q.correct_answer
        button2.text = q.incorrect_answers[0]
        button3.text = q.incorrect_answers[1]
        button4.text = q.incorrect_answers[2]
    }
}