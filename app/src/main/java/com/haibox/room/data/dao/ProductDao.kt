package com.haibox.room.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.haibox.room.data.entity.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Product): Long

    @Query("delete from product where id = :id")
    fun delete(id: Long)

    @Update
    fun update(item: Product): Int

    @Transaction
    fun insertOrUpdate(id: Long, item: Product) {
        val id = insert(item)
        if (id == -1L) {
            update(item)
        }
    }

    @Query("select * from Product where id = :id")
    fun getItem(id: Long): Product?
}
