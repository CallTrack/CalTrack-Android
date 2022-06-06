package com.capstone.caltrack.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.capstone.caltrack.User
import java.util.*

@Entity(tableName = "record")
class RecordEntity (

    @field:ColumnInfo(name = "idRecord")
    @field:PrimaryKey
    val idRecord: String,

    @field:ColumnInfo(name = "date")
    val date: Date,

    @field:ColumnInfo(name = "caloriesIn")
    val caloriesIn: Int,

    @field:ColumnInfo(name = "caloriesBurn")
    val caloriesBurn: Int,

    @field:ColumnInfo(name = "caloriesTotal")
    val caloriesTotal: Int
)