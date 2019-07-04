package com.alvarodziadzio.todoapp

import androidx.room.*

@Entity
data class Item(
    var title: String,
    var description: String,
    var isComplete: Boolean
) {
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0
}

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll(): List<Item>

    @Query("SELECT * FROM Item WHERE id IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<Item>

    @Insert
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)
}

@Database(entities = arrayOf(Item::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}