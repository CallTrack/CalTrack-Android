package com.capstone.caltrack.ui.register

import androidx.lifecycle.ViewModel
import com.capstone.caltrack.data.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {

//    val _result = MutableLiveData<Result<ApiResponse>>()
//    val result: LiveData<Result<ApiResponse>>
//        get() = _result
//
//    fun register(email: String, name: String, gender: String, age: Int, weight: Float, height: Float, activityLevel: String) {
//        _result.postValue(Result.Loading(true))
//        try {
//            val response = repository.register(email, name, gender, age, weight, height, activityLevel)
//            _result.postValue(Result.Success(response))
//        } catch (e: Exception) {
//            _result.postValue(Result.Error(e))
//        } finally {
//            _result.postValue(Result.Loading(false))
//        }
//    }

    fun register(email: String, name: String, gender: String, age: Int, weight: Float, height: Float, activityLevel: String) = repository.register(email, name, gender, age, weight, height, activityLevel)
}
