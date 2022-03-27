package com.dummyproject.apis

import okhttp3.MultipartBody


class AuthRepository(private val api : ApiCall)  {
//    suspend fun fetchData() = safeApiCall { api.getData() }
    suspend fun getFetchData() =  api.getData()
    suspend fun getFetchDataList() =  api.getDataList()
    suspend fun getSaveData(pic: MultipartBody.Part) =  api.updateProfile(pic)

}