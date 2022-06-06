package com.capstone.caltrack.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.caltrack.ApiResponse
import com.capstone.caltrack.data.local.room.AppDatabase
import com.capstone.caltrack.data.local.SessionManager
import com.capstone.caltrack.User
import com.capstone.caltrack.data.remote.api.ApiService
import java.lang.Exception

class Repository (
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) {

    fun login(email: String): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email)
            sessionManager.setUser(response)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "login: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

//    suspend fun login(email: String) = apiService.login(email)

    fun register(email: String, name: String, gender: String, age: Int, weight: Float, height: Float, activityLevel: String): LiveData<Result<ApiResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(email, name, gender, age, weight, height, activityLevel)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "signup: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

//    suspend fun register(email: String, name: String, gender: String, age: Int, weight: Float, height: Float, activityLevel: String) = apiService.register(email, name, gender, age, weight, height, activityLevel)

    fun getUser() : User? {
        val user = sessionManager.getUser()
        return user
    }

    companion object{
        const val TAG = "Repository"

        @Volatile
        private var instance: Repository?= null
        fun getInstance(
            apiService: ApiService,
            appDatabase: AppDatabase,
            sessionManager: SessionManager
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, appDatabase, sessionManager)
            }.also { instance = it }
    }
}