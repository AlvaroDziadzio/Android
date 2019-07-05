package com.alvarodziadzio.trivia.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.alvarodziadzio.trivia.Game
import com.alvarodziadzio.trivia.HttpRequest
import com.alvarodziadzio.trivia.HttpRequestTask
import com.alvarodziadzio.trivia.R
import com.alvarodziadzio.trivia.questionProvider.QuestionFilter
import kotlinx.android.synthetic.main.fragment_settings.*
import org.json.JSONObject

class SettingsFragment: Fragment() {

    val ids = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val param = HttpRequest("https://opentdb.com/api_category.php")

        HttpRequestTask(param) {

            if (it != null) {

                var list = mutableListOf<String>("any")

                val arr = it.getJSONArray("trivia_categories")
                for (i in 0 until arr.length()) {
                    val obj = arr[i] as JSONObject
                    list.add(obj.getString("name"))
                    ids.add(obj.getInt("id").toString())
                }

                val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, list)
                category.adapter = adapter


            }

        }.execute()

        update.setOnClickListener {

            val filter = QuestionFilter(10)

            filter.difficulty = when (radio_difficulty.checkedRadioButtonId) {
                easy.id -> "easy"
                medium.id -> "medium"
                hard.id -> "hard"
                else -> null
            }

            filter.type = when (radio_type.checkedRadioButtonId) {
                bool.id -> "boolean"
                multiple.id -> "multiple"
                else -> null
            }

            filter.category = when (category.selectedItem.toString()) {
                "any" -> null
                else -> ids[category.selectedItemPosition-1]
            }


            Game.provider.filter = filter

        }

    }
}