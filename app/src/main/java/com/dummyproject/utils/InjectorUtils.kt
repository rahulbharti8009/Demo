package com.dummyproject.utils

import com.dummyproject.apis.ApiCall
import com.dummyproject.apis.AuthRepository
import com.dummyproject.viewmodel.MyViewModelFactory

object InjectorUtils {
    fun provideLoginViewModelFactory() : MyViewModelFactory {
        val authRepository = AuthRepository(api = ApiCall.create())
        return MyViewModelFactory(authRepository)
    }
}