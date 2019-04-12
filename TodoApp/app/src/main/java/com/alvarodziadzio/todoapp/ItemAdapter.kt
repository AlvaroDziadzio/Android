package com.alvarodziadzio.todoapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val list: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo, parent, false)
        val holder = ViewHolder(view)
        context = parent.context
        return holder

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.title.setText(item.title)
        holder.description.setText(item.description)
        holder.card.setOnClickListener {
            Log.d("APP", "Card $position clicked")
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: EditText
        val description: EditText
        val card: CardView

        init {
            title = view.findViewById(R.id.title)
            description = view.findViewById(R.id.description)
            card = view.findViewById(R.id.todoCard)
        }

    }

}