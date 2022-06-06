package com.capstone.caltrack.ui.login

import androidx.lifecycle.ViewModel
import com.capstone.caltrack.data.Repository

class LoginViewModel(private val repository: Repository): ViewModel() {

//    val _users = MutableLiveData<Result<User>>()
//    val users: LiveData<Result<User>>
//        get() = _users
//
//    fun login(email: String) {
//        _users.postValue(Result.Loading(true))
//        try {
//            val user = repository.login(email)
//            _users.postValue(Result.Success(user))
//        } catch (e: Exception) {
//            _users.postValue(Result.Error(e))
//        } finally {
//            _users.postValue(Result.Loading(false))
//        }
//    }

    fun login(email: String) = repository.login(email)
}
