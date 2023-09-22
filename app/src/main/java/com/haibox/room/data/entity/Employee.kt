package com.haibox.room.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Employee (
    @PrimaryKey
    var identity: Long, // Person ID
    var gender: Int = 0,
    var username: String,
    var age: Int = 0,
    var salary: Long = 1000
) {
    override fun toString(): String {
        return "Person(identity=$identity, gender=$gender, username='$username', age=$age, salary=$salary)"
    }
}