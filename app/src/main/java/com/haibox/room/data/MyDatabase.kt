package com.haibox.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.haibox.room.data.dao.EmployeeDao
import com.haibox.room.data.dao.ProductDao
import com.haibox.room.data.entity.Employee
import com.haibox.room.data.entity.Product


@Database(entities = [Employee::class, Product::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun EmployeeDao(): EmployeeDao
    abstract fun ProductDao(): ProductDao

    companion object {
        private const val DB_NAME = "HaiboxTest.db"

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java, DB_NAME
                )
//                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
//                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `NewTable` " +
                            "(`id` TEXT PRIMARY KEY NOT NULL, `pid` INTEGER NOT NULL)"
                )
            }
        }
    }
}