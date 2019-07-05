package com.alvarodziadzio.trivia.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvarodziadzio.trivia.R
import com.alvarodziadzio.trivia.data.Question

@SuppressLint("ValidFragment")
class GameBooleanFragment(val q: Question): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_boolean, container, false)
    }

}