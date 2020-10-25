package com.dummyproject.apis


class AuthRepository(private val api : ApiCall)  {

//    suspend fun fetchData() = safeApiCall { api.getData() }

    suspend fun getFetchData() =  api.getData()
    suspend fun getLogin() =  api.getLogin()

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}