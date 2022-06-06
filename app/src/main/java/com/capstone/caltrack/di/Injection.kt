package com.capstone.caltrack.di

import android.content.Context
import com.capstone.caltrack.data.local.room.AppDatabase
import com.capstone.caltrack.data.Repository
import com.capstone.caltrack.data.local.SessionManager
import com.capstone.caltrack.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = AppDatabase.getInstance(context)
        val sessionManager = SessionManager(context)
        return Repository.getInstance(apiService, database, sessionManager)
    }
}