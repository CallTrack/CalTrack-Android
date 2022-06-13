package com.capstone.caltrack.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.caltrack.data.Repository
import com.capstone.caltrack.data.local.entity.RecordEntity
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val repository: Repository): ViewModel() {

    fun getRecordOnline() = repository.getRecordOnline()

    fun getRecordRoom(): LiveData<List<RecordEntity>> = repository.getRecordRoom()

    fun addNewRecord(date: String) {
        viewModelScope.launch {
            repository.addNewRecord(date)
        }
    }

    fun postRecord(
        date: String, caloriesIn: Int, caloriesBurn: Int, caloriesTotal: Int
    ) = repository.postRecord(date, caloriesIn, caloriesBurn, caloriesTotal)

}