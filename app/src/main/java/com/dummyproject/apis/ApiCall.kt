package com.dummyproject.apis

import com.dummyproject.Abc.Companion.readKeyStore
import com.dummyproject.BuildConfig
import com.dummyproject.entity.LoginEntity
import com.dummyproject.entity.UploadPicEntity
import com.dummyproject.utils.ApiName.login
import com.dummyproject.utils.Constant
import com.dummyproject.utils.Constant.Companion.paading
import com.dummyproject.utils.Constant.Companion.url
import com.dummyproject.utils.Constant.Companion.vedio
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import okhttp3.RequestBody

import okhttp3.MultipartBody

import okhttp3.ResponseBody

import retrofit2.http.POST

import retrofit2.http.Multipart
import java.io.FileInputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.util.*
import javax.net.ssl.*
import kotlin.collections.ArrayList


interface ApiCall {


    @GET()
    suspend fun getRegion(@Url string: String): List<String>

    @GET("todos/1")
    suspend fun getData(): LoginEntity

    @GET("todos")
    suspend fun getDataList(): ArrayList<LoginEntity>

//    @GET("api/users")
//    suspend fun getListData(@Query("page") pageNumber: Int): PaggingEntity


    @Multipart
    @POST("https://ataljalapi.cloud.kreatetechnologies.com/api/upload/imageupload")
    suspend  fun updateProfile(
        @Part image: MultipartBody.Part,
    ): UploadPicEntity

    companion object {
//        private const val BASE_URL = "https://reqres.in/"

        fun create(): ApiCall {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
//                .sslSocketFactory(sslSocketFactory, trustManager)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(object  : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        val requestBuilder: Request.Builder = chain.request().newBuilder()
//                        requestBuilder.header("Authorization", "serverKey")
//                        requestBuilder.header("Content-Type", "application/json")
                        requestBuilder.header("Content-Type", "application/json; charset=utf-8")
                        return chain.proceed(requestBuilder.build())
                    }
                })
                .build()


            return Retrofit.Builder()

                .baseUrl(BuildConfig.BASEURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiCall::class.java)
        }
    }

    //    ================================

}
