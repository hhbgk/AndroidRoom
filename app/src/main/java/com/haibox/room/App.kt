package com.haibox.room

import android.app.Application
import com.haibox.room.data.MyDatabase

class App: Application() {
    val database: MyDatabase by lazy { MyDatabase.getInstance(this) }

    companion object {
        private lateinit var application: App
        fun getApp() = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}