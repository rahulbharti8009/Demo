package com.dummyproject.apis

import com.dummyproject.BuildConfig
import com.dummyproject.entity.LoginEntity
import com.dummyproject.utils.ApiName.login
import com.dummyproject.utils.Constant
import com.dummyproject.utils.Constant.Companion.paading
import com.dummyproject.utils.Constant.Companion.url
import com.dummyproject.utils.Constant.Companion.vedio
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiCall {

    @GET()
    suspend fun getCountry(@Url string: String): List<String>

    @GET()
    suspend fun getRegion(@Url string: String): List<String>

    @GET("todos/1")
    suspend fun getData(): LoginEntity


    @FormUrlEncoded
    @POST(login)
    suspend fun getLogin(): LoginEntity

//    @GET("api/users")
//    suspend fun getListData(@Query("page") pageNumber: Int): PaggingEntity


    companion object {
        private const val BASE_URL = "https://reqres.in/"

        fun create(): ApiCall {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(object  : Interceptor {
//                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//                        val requestBuilder: Request.Builder = chain.request().newBuilder()
////                        requestBuilder.header("Authorization", "serverKey")
////                        requestBuilder.header("Content-Type", "application/json")
//                        return chain.proceed(requestBuilder.build())
//                    }
//                })
                .build()

            return Retrofit.Builder()
                .baseUrl(
                    when (Constant.BASEURL) {
                        url -> BuildConfig.BASEURL
                        paading -> BuildConfig.BASEURL_pagging
                        vedio -> BuildConfig.BASEURL_video
                        else -> {
                            BuildConfig.BASEURL
                        }
                    }
                )
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiCall::class.java)
        }
    }
}
