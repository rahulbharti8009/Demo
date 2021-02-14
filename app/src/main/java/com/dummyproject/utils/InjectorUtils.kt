package com.dummyproject.utils

import com.dummyproject.apis.ApiCall
import com.dummyproject.apis.AuthRepository
import com.dummyproject.viewmodel.LoginViewModelFactory

object InjectorUtils {

    fun provideLoginViewModelFactory() : LoginViewModelFactory{
        val authRepository = AuthRepository(api = ApiCall.create())
        return LoginViewModelFactory(authRepository)
    }

}