package com.haibox.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.haibox.room.data.MyDatabase
import com.haibox.room.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var dataDialog: DataDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            dataDialog = DataDialog()
            dataDialog?.show(supportFragmentManager, "dataDialog")
        }

        binding.btnDelete.setOnClickListener {
            App.getApp().database.EmployeeDao().delete(1)
        }

        val text = StringBuilder()
        binding.btnShow.setOnClickListener {
            text.clear()
            binding.tvShow.text = ""
            val list = App.getApp().database.EmployeeDao().queryAll()
            Log.i(TAG, "Show button:${list?.size}")
            if (list != null) {
                for (item in list) {
                    text.append(item.toString() + "\n")
                }
                binding.tvShow.text = text
            }
        }
    }
}