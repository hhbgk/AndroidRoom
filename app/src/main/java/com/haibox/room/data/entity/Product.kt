package com.haibox.room.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    var id: Long,
    var name: String,
    var type: Int,
    var color: String
) {
    override fun toString(): String {
        return "Product(id=$id, name='$name', type=$type, color='$color')"
    }

}
