package com.dummyproject.viewmodel

import androidx.lifecycle.*
import com.dummyproject.apis.AuthRepository
import com.dummyproject.apis.Resource
import com.dummyproject.entity.LoginEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

 private val _loginResponse : MutableLiveData<Resource<LoginEntity>> = MutableLiveData()
 private val loginResponse : LiveData<Resource<LoginEntity>>
    get() = _loginResponse


    fun login() = viewModelScope.launch {
//        _loginResponse.value = repository.fetchData()
    }

    fun getLoginData(mobile : String, token : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getLoginData(mobile, token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



}