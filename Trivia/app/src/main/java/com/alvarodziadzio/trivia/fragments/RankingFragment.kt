package com.alvarodziadzio.trivia.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alvarodziadzio.trivia.HttpWorkbench
import com.alvarodziadzio.trivia.R
import kotlinx.android.synthetic.main.fragment_ranking.*
import org.json.JSONObject

class RankingFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HttpWorkbench.getRanking {

            Log.d("Ranking", it.toString())
            if (it != null) {

                val names = mutableListOf<String>()

                ranking_layout.removeAllViews()
                val arr = it.getJSONArray("ranking")
                for (i in 0 until arr.length()) {
                    val obj = arr[i] as JSONObject

                    val tv = TextView(context)
                    tv.text = obj.getString("nome")
                    ranking_layout.addView(tv)

                }

            }

        }

    }
}