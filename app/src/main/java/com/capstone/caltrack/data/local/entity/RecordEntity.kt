package com.capstone.caltrack.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "record")
class RecordEntity (

    @field:PrimaryKey
    @field:ColumnInfo(name = "date")
    val date: String,

    @field:ColumnInfo(name = "caloriesIn")
    val caloriesIn: Int,

    @field:ColumnInfo(name = "caloriesBurn")
    val caloriesBurn: Int,

    @field:ColumnInfo(name = "caloriesTotal")
    val caloriesTotal: Int
)