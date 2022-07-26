package com.capstone.caltrack.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.caltrack.data.Repository
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.data.local.entity.ExerciseEntity
import kotlinx.coroutines.launch

class ExerciseViewModel (private val repository: Repository): ViewModel() {
    private val _result = MutableLiveData<Result<ExerciseEntity>>()
    val result: LiveData<Result<ExerciseEntity>> = _result


    fun getExercise() = repository.getExercise()

    fun saveExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            repository.setExerciseBookmark(exercise, true)
        }
    }

    fun deleteExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            repository.setExerciseBookmark(exercise, false)
        }
    }
}