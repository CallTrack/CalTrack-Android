package com.capstone.caltrack.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.capstone.caltrack.data.local.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise ORDER BY isBookmarked DESC")
    fun getExercise(): LiveData<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(exercise: List<ExerciseEntity>)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Query("SELECT EXISTS(SELECT * FROM exercise WHERE name = :name AND isBookmarked = 1)")
    suspend fun isBookmarked(name: String): Boolean

    @Query("DELETE FROM exercise WHERE isBookmarked = 0")
    suspend fun deleteAll()
}