package com.dummyproject.viewmodel

import androidx.lifecycle.*
import com.dummyproject.apis.AuthRepository
import com.dummyproject.apis.Resource
import com.dummyproject.entity.LoginEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {


    fun getFetchData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getFetchData()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



}