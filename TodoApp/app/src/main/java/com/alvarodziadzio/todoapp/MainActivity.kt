package com.alvarodziadzio.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    lateinit var adapter: ItemAdapter
    lateinit var rview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: MutableList<Item> = mutableListOf()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-app"
        ).build()

        /*val list = */db.itemDao().insertAll(Item(1, "Foda", "Foda", false))

        adapter = ItemAdapter(list)
        adapter.context = this

        rview = findViewById(R.id.recyclerView)
        rview.layoutManager = LinearLayoutManager(this)
        rview.itemAnimator = DefaultItemAnimator()
        rview.adapter = adapter

    }

    fun addNew(view: View) {
        adapter.list.add(Item(
            adapter.itemCount,
            "",
            "",
            false
        ))

        adapter.notifyItemInserted(adapter.itemCount - 1)
        adapter.editPos = adapter.itemCount - 1
        rview.scrollToPosition(adapter.itemCount -1)
    }

}
