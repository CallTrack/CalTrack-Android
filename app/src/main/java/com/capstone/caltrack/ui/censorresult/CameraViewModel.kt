package com.capstone.caltrack.ui.censorresult

import android.util.Log
import androidx.lifecycle.*
import com.capstone.caltrack.data.Repository
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.data.remote.api.ApiConfig
import com.capstone.caltrack.data.remote.response.Food
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CameraViewModel (private val repository: Repository): ViewModel() {

    private val _result = MutableLiveData<Result<Food>>()
    val result: LiveData<Result<Food>> = _result

    fun predictImage(imageMultiPart: MultipartBody.Part) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiServiceCensor().predictImage(imageMultiPart)
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                Log.d(TAG, "Exception: ${e.message.toString()}")
                _result.value = Result.Error(e.message.toString())
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

    companion object{
        private const val TAG = "CameraViewModel"
    }
}