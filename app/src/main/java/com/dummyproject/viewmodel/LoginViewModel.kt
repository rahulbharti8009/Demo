package com.dummyproject.viewmodel

import androidx.lifecycle.*
import com.dummyproject.apis.AuthRepository
import com.dummyproject.apis.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    fun getFetchData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getFetchData()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getFetchDataList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getFetchDataList()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getSavePic(pic: MultipartBody.Part) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getSaveData(pic)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}