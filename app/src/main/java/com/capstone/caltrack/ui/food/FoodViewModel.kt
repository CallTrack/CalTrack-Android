package com.capstone.caltrack.ui.food

import android.util.Log
import androidx.lifecycle.*
import com.capstone.caltrack.data.Repository
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.ui.register.RegisterViewModel
import kotlinx.coroutines.launch

class FoodViewModel (private val repository: Repository): ViewModel() {

    private val _food = MutableLiveData<Result<List<FoodEntity>>>()
    val food: LiveData<Result<List<FoodEntity>>> get() = _food

    fun getFood(mealTime: String) = repository.getFood(mealTime)

    fun searchFood(food: String, mealTime: String) {
        viewModelScope.launch {
            _food.postValue(Result.Loading)
            try {
                val result = repository.searchFood(food)
                val foodList = result.foodList.map { foods ->
                    FoodEntity(
                        foods.idFood,
                        foods.name,
                        foods.calories,
                        false,
                        mealTime
                    )
                }
                _food.value = Result.Success(foodList)
            } catch (e: Exception) {
                Log.d(RegisterViewModel.TAG, "searchFood: ${e.message.toString()}")
                _food.value = Result.Error(e.message.toString())
            }
        }
    }

    fun addMeal(meal : List<FoodEntity>) {
        viewModelScope.launch {
            repository.addMeal(meal)
        }
    }

    fun updateRecord(date: String, calorie: Int) {
        viewModelScope.launch {
            repository.updateRecord(date, calorie, "add")
        }
    }
}