package com.alvarodziadzio.todoapp

import androidx.room.*

@Entity
data class Item(
    @PrimaryKey
    var id: Int = 0,

    var title: String,
    var description: String,
    var isComplete: Boolean
)

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll(): List<Item>

    @Query("SELECT * FROM Item WHERE id IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<Item>

    @Insert
    fun insertAll(vararg itens: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)
}

@Database(entities = arrayOf(Item::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}