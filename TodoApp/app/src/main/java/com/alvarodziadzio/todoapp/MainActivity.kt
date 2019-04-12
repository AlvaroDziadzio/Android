package com.alvarodziadzio.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var adapter: ItemAdapter
    lateinit var rview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: MutableList<Item> = mutableListOf()

        for (i in 0..5) {
            val it = Item(
                i,
                i.toString(),
                i.toString(),
                false
            )

            list.add(it)
        }

        adapter = ItemAdapter(list)

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
