package com.capstone.caltrack.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.capstone.caltrack.data.local.entity.RecordEntity
import java.util.*

@Dao
interface RecordDao {
    @Query("SELECT * FROM record ORDER BY date DESC")
    fun getRecord(): LiveData<List<RecordEntity>>

    @Query("SELECT * FROM record WHERE date = :date")
    suspend fun getSpecificRecord(date: String): RecordEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecordList(record: List<RecordEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecord(record: RecordEntity)

    @Update
    suspend fun updateRecord(record: RecordEntity)

    @Query("DELETE FROM record")
    suspend fun deleteAll()
}