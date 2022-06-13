package com.capstone.caltrack.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.caltrack.di.Injection
import com.capstone.caltrack.data.Repository
import com.capstone.caltrack.ui.addcalorie.AddCalorieViewModel
import com.capstone.caltrack.ui.censorresult.CameraViewModel
import com.capstone.caltrack.ui.exercise.ExerciseViewModel
import com.capstone.caltrack.ui.food.FoodViewModel
import com.capstone.caltrack.ui.login.LoginViewModel
import com.capstone.caltrack.ui.main.MainViewModel
import com.capstone.caltrack.ui.register.RegisterViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                MainViewModel(repository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                LoginViewModel(repository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                RegisterViewModel(repository) as T
            modelClass.isAssignableFrom(AddCalorieViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                AddCalorieViewModel(repository) as T
            modelClass.isAssignableFrom(FoodViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                FoodViewModel(repository) as T
            modelClass.isAssignableFrom(ExerciseViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                ExerciseViewModel(repository) as T
            modelClass.isAssignableFrom(CameraViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                CameraViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}