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
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todo.db"
        ).allowMainThreadQueries().build()

        val list: MutableList<Item> = db.itemDao().getAll().toMutableList()

        //val list: MutableList<Item> = mutableListOf()

        adapter = ItemAdapter(list)
        adapter.context = this
        adapter.db = db

        rview = findViewById(R.id.recyclerView)
        rview.layoutManager = LinearLayoutManager(this)
        rview.itemAnimator = DefaultItemAnimator()
        rview.adapter = adapter

    }

    fun addNew(view: View) {
        adapter.list.add(Item(
            "",
            "",
            false
        ))

        adapter.notifyItemInserted(adapter.itemCount - 1)
        adapter.editPos = adapter.itemCount - 1
        rview.scrollToPosition(adapter.itemCount -1)
    }

}
