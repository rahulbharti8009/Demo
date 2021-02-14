package com.dummyproject.apis


class AuthRepository(private val api : ApiCall)  {

//    suspend fun fetchData() = safeApiCall { api.getData() }

    suspend fun getFetchData() =  api.getData()

}