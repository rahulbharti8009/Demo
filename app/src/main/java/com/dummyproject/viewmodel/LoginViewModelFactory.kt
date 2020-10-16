package com.dummyproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dummyproject.apis.AuthRepository

class LoginViewModelFactory(private val authRepository: AuthRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return  when{
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                return LoginViewModel(
                authRepository
            ) as T
            }
            else ->{ throw IllegalArgumentException("Unknown class name")}
        }
    }
}