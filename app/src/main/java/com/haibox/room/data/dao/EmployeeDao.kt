package com.haibox.room.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.haibox.room.data.entity.Employee


@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Employee): Long

    @Query("DELETE from Employee where identity = :id")
    fun delete(id: Long)

    @Update
    fun update(item: Employee): Int

    @Transaction
    fun insertOrUpdate(id: Long, item: Employee) {
        val id = insert(item)
        if (id == -1L) {
            update(item)
        }
    }

    @Query("SELECT * FROM Employee where identity = :id")
    fun getItem(id: Long): Employee?

    @Query("select * from Employee")
    fun queryAll(): List<Employee>?
}