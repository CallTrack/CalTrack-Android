package com.capstone.caltrack.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.capstone.caltrack.data.local.SessionManager
import com.capstone.caltrack.data.local.entity.ExerciseEntity
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.data.local.entity.RecordEntity
import com.capstone.caltrack.data.local.room.AppDatabase
import com.capstone.caltrack.data.remote.response.User
import com.capstone.caltrack.data.remote.api.ApiService
import com.capstone.caltrack.data.remote.response.ApiResponse
import java.util.*

class Repository (
    private val apiService: ApiService,
    private val database: AppDatabase,
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

    suspend fun register(email: String, name: String, gender: String, age: Int, weight: Float, height: Float, activityLevel: String) = apiService.register(email, name, gender, age, weight, height, activityLevel)

    fun getMeal(mealTime: String): LiveData<List<FoodEntity>> {
        return database.foodDao().getMeal(mealTime)
    }

    suspend fun addMeal(meal: List<FoodEntity>) {
        database.foodDao().insertFood(meal)
    }

    fun getFood(mealTime: String): LiveData<Result<List<FoodEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFood()
            val foodList = response.foodList.map { food ->
                FoodEntity(
                    food.idFood,
                    food.name,
                    food.calories,
                    false,
                    mealTime
                )
            }
            emit(Result.Success(foodList))
        } catch (e: Exception) {
            Log.d(TAG, "getFood: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun searchFood(food: String) = apiService.searchFood(food)

    fun getExercise(): LiveData<Result<List<ExerciseEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getExercises()
            val exerciseList = response.exerciseList.map { exercise ->
                val isBookmarked = database.exerciseDao().isBookmarked(exercise.name)
                ExerciseEntity(
                    exercise.id_exercise,
                    exercise.name,
                    exercise.caloriesPerMinute,
                    isBookmarked
                )
            }
            database.exerciseDao().deleteAll()
            database.exerciseDao().insertExercise(exerciseList)
        } catch (e: Exception) {
            Log.d(TAG, "getExercise: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<ExerciseEntity>>> = database.exerciseDao().getExercise().map { Result.Success(it) }
        emitSource(localData)
    }

    suspend fun setExerciseBookmark(exercise: ExerciseEntity,  bookmarkState: Boolean) {
        exercise.isBookmarked = bookmarkState
        database.exerciseDao().updateExercise(exercise)
    }

    fun getRecordOnline(): LiveData<Result<List<RecordEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getRecord(sessionManager.getID())
            val recordList = response.recordList.map { record ->
                RecordEntity(
                    record.date,
                    record.caloriesIn,
                    record.caloriesBurn,
                    record.totalCalories
                )
            }
            emit(Result.Success(recordList))
        } catch (e: Exception) {
            Log.d(TAG, "getRecord: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getRecordRoom(): LiveData<List<RecordEntity>> = database.recordDao().getRecord()

    fun postRecord(date: String, caloriesIn: Int, caloriesBurn: Int, caloriesTotal: Int): LiveData<Result<ApiResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addRecord(sessionManager.getID(), date, caloriesIn, caloriesBurn, caloriesTotal)
            database.recordDao().deleteAll()
            database.foodDao().deleteAll()
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "postRecord: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun addNewRecord(date: String) {
        val record = RecordEntity(date, 0, 0, 0)
        database.recordDao().insertRecord(record)
    }

    suspend fun updateRecord(date: String, calorie: Int, type: String) {
        val record = database.recordDao().getSpecificRecord(date)
        var calTotal = record.caloriesTotal
        var calIn = record.caloriesIn
        var calBurn = record.caloriesBurn
        when (type) {
            "add" -> {
                calTotal += calorie
                calIn += calorie
            }
            "burn" -> {
                calTotal -= calorie
                calBurn += calorie
            }
        }
        val updatedRecord = RecordEntity(date, calIn, calBurn, calTotal)
        database.recordDao().updateRecord(updatedRecord)
    }

    companion object{
        const val TAG = "Repository"

        @Volatile
        private var instance: Repository?= null
        fun getInstance(
            apiService: ApiService,
            database: AppDatabase,
            sessionManager: SessionManager
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, database, sessionManager)
            }.also { instance = it }
    }
}