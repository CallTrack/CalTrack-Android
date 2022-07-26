package com.capstone.caltrack.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
class ExerciseEntity (
    @field:ColumnInfo(name = "idExercise")
    @field:PrimaryKey
    val idExercise: Int,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "calories")
    val calories: Int,

    @field:ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean
)