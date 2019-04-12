package com.alvarodziadzio.todoapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val list: MutableList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var editPos = -1

    private val VIEW_EDIT = 0
    private val VIEW_SHOW = 1

    override fun getItemViewType(position: Int): Int {
        if (position == editPos)
            return VIEW_EDIT
        return VIEW_SHOW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_EDIT)
            return createEditView(parent)
        return createShowView(parent)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        if (holder is EditView) {
            holder.title.setText(item.title)
            holder.description.setText(item.description)
            holder.save.setOnClickListener {
                item.title= holder.title.text.toString()
                item.description = holder.description.text.toString()
            }
        }
        else if (holder is ShowView) {
            holder.title.setText(item.title)
            holder.description.setText(item.description)
            holder.delete.setOnClickListener {
                Log.d("APP", "Card $position aaaaaaaaaa")
                list.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(0, list.size)
                editPos = -1
            }
            holder.card.setOnClickListener {
                editPos = position
                notifyItemChanged(position)
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////
    // Helper Functions

    private fun createEditView(parent: ViewGroup) : EditView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_edit, parent, false)
        return EditView(view)
    }

    private fun createShowView(parent: ViewGroup) : ShowView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_show, parent, false)
        return ShowView(view)
    }



    ///////////////////////////////////////////////////////////////////////
    // Classes

    inner class EditView(view: View) : RecyclerView.ViewHolder(view) {

        val title: EditText
        val description: EditText
        val card: CardView
        val save: ImageButton

        init {
            title = view.findViewById(R.id.titleEdit)
            description = view.findViewById(R.id.descriptionEdit)
            card = view.findViewById(R.id.todoCard)
            save = view.findViewById(R.id.saveEdit)
        }

    }

    inner class ShowView(view: View) : RecyclerView.ViewHolder(view) {

        val title: CheckBox
        val description: TextView
        val card: CardView
        val delete: ImageButton

        init {
            title = view.findViewById(R.id.titleShow)
            description = view.findViewById(R.id.descriptionShow)
            card = view.findViewById(R.id.todoCard)
            delete = view.findViewById(R.id.deleteShow)
        }

    }

}