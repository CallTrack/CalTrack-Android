package com.capstone.caltrack.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
class FoodEntity (

        @field:ColumnInfo(name = "idFood")
        @field:PrimaryKey
        val idFood: Int,

        @field:ColumnInfo(name = "name")
        val name: String,

        @field:ColumnInfo(name = "calories")
        val calories: Int,

        @field:ColumnInfo(name = "selected")
        var selected: Boolean,

        @field:ColumnInfo(name = "time")
        val time: String?
)