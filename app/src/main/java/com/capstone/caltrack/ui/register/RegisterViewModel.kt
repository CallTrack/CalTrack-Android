package com.capstone.caltrack.ui.register

import android.util.Log
import androidx.lifecycle.*
import com.capstone.caltrack.data.Repository
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.data.remote.response.ApiResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository): ViewModel() {

    private val _result = MutableLiveData<Result<ApiResponse>>()
    val result: LiveData<Result<ApiResponse>> = _result

    fun register(email: String, name: String, gender: String, age: Int, weight: Float, height: Float, activityLevel: String) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = repository.register(email, name, gender, age, weight, height, activityLevel)
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                Log.d(TAG, "register: ${e.message.toString()}")
                _result.value = Result.Error(e.message.toString())
            }
        }
    }

    companion object {
        const val TAG = "RegisterViewModel"
    }
}
