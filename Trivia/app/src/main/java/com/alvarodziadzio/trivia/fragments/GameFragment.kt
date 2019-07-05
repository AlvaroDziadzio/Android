package com.alvarodziadzio.trivia.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvarodziadzio.trivia.R
import com.alvarodziadzio.trivia.data.Question
import kotlinx.android.synthetic.main.fragment_game.*
import android.animation.ObjectAnimator
import android.widget.Button
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.alvarodziadzio.trivia.Game




class GameFragment(): Fragment() {

    lateinit var q: Question

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setQuestion(Game.nextRemote()!!)

    }

    private fun setBoolQuestion(q: Question) {

        buttons.removeAllViews()
        val ans = mutableListOf(q.correct_answer, q.incorrect_answers[0])
        val btns = listOf(Button(context), Button(context))

        ans.shuffle()
        for (i in btns) {
            val qText = ans.removeAt(0)
            i.text = qText
            i.setOnClickListener {
                Game.answer(qText)
                handleAnswer()
                if (qText != q.correct_answer)
                    it.backgroundTintList = ContextCompat.getColorStateList(context!!, R.color.colorAccent)
                else
                    it.backgroundTintList = ContextCompat.getColorStateList(context!!, R.color.colorPrimary)
            }
            buttons.addView(i)
        }

    }

    private fun setMultipleQuestion(q: Question) {

        buttons.removeAllViews()
        val ans = mutableListOf(q.correct_answer, q.incorrect_answers[0], q.incorrect_answers[1], q.incorrect_answers[2])
        val btns = listOf(Button(context), Button(context), Button(context), Button(context))

        ans.shuffle()
        for (i in btns) {
            val qText = ans.removeAt(0)
            i.text = qText
            i.setOnClickListener {
                Game.answer(qText)
                handleAnswer()
                if (qText != q.correct_answer)
                    it.backgroundTintList = ContextCompat.getColorStateList(context!!, R.color.colorAccent)
                else
                    it.backgroundTintList = ContextCompat.getColorStateList(context!!, R.color.colorPrimary)
            }
            buttons.addView(i)
        }

    }

    private fun handleAnswer() {

        for (i in buttons.children) {
            i.setOnClickListener {}
        }

        auxButton1.text = "Continue"
        auxButton1.setOnClickListener {
            setQuestion(Game.nextRemote()!!)
        }

        if (Game.hasLater()) {
            auxButton2.visibility = View.VISIBLE
            auxButton2.text = context!!.getText(R.string.title_game)
            auxButton2.setOnClickListener {
                setQuestion(Game.nextLater()!!)
            }
        }


    }

    private fun setQuestion(q: Question) {

        if (q.type == "multiple")
            setMultipleQuestion(q)
        else
            setBoolQuestion(q)

        question.text = q.question

        auxButton1.text = "Answer Later"
        auxButton1.setOnClickListener {
            Game.answerLater()
            setQuestion(Game.nextRemote()!!)
        }

        auxButton2.visibility = View.INVISIBLE

        val animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animation.duration = Game.time
        animation.start()

    }



}