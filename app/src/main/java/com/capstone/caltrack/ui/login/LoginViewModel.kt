package com.capstone.caltrack.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.caltrack.data.Repository
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: Repository): ViewModel() {

    fun login(email: String) = repository.login(email)

    fun addNewRecord(date: String) {
        viewModelScope.launch {
            repository.addNewRecord(date)
        }
    }
}
