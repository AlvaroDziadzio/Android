package com.alvarodziadzio.todoapp

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val list: MutableList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var editPos = -1
    lateinit var context: Context
    lateinit var db: AppDatabase

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
        val lItem = list[position]

        if (holder is EditView) {

            holder.title.setText(lItem.title)
            holder.description.setText(lItem.description)

            holder.save.setOnClickListener {
                lItem.title= holder.title.text.toString()
                lItem.description = holder.description.text.toString()
                editPos = -1
                if (db.itemDao().loadAllByIds(intArrayOf(lItem.id)).isEmpty())
                    db.itemDao().insert(lItem)
                else
                    db.itemDao().update(lItem)
                notifyItemChanged(position)
            }

        }
        else if (holder is ShowView) {

            holder.title.setText(lItem.title)

            if (lItem.isComplete) {
                holder.title.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                holder.delete.background = ContextCompat.getDrawable(context, R.drawable.ic_share)
                holder.delete.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Oba! Acabei de concluir: ${lItem.title}")
                        type = "text/plain"
                    }
                    startActivity(context, sendIntent, null)
                }
            } else {
                holder.title.setTextColor(ContextCompat.getColor(context, R.color.primary_text_default_material_light))
                holder.delete.background = ContextCompat.getDrawable(context, R.drawable.ic_delete_black_24dp)
                holder.delete.setOnClickListener {
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(0, list.size)
                    db.itemDao().delete(lItem)
                    editPos = -1
                }
            }



            holder.card.setOnLongClickListener {
                lItem.isComplete = !lItem.isComplete
                notifyItemChanged(position)
                db.itemDao().update(lItem)
                true
            }

            holder.card.setOnClickListener {
                editPos = position
                notifyItemChanged(position)
                lItem.isComplete = false
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

        val title: TextView
        val card: CardView
        val delete: ImageButton

        init {
            title = view.findViewById(R.id.titleShow)
            card = view.findViewById(R.id.todoCard)
            delete = view.findViewById(R.id.deleteShow)
        }

    }

}