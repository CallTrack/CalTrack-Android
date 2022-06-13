package com.capstone.caltrack.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.capstone.caltrack.data.local.entity.FoodEntity

@Dao
interface FoodDao {
    @Query("SELECT * FROM food WHERE time = :mealTime")
    fun getMeal(mealTime: String): LiveData<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFood(foods: List<FoodEntity>)

    @Query("DELETE FROM food")
    suspend fun deleteAll()
}