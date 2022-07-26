package com.capstone.caltrack.ui.addcalorie

import androidx.lifecycle.ViewModel
import com.capstone.caltrack.data.Repository

class AddCalorieViewModel (private val repository: Repository): ViewModel() {

    fun getMeal(mealTime: String) = repository.getMeal(mealTime)
}