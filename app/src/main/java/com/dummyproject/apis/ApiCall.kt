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


interface ApiCall {

    @GET()
    suspend fun getCountry(@Url string: String): List<String>

    @GET()
    suspend fun getRegion(@Url string: String): List<String>

    @GET("todos/1")
    suspend fun getData(): LoginEntity

    @POST("login")
    suspend fun getData(@Body str : String): LoginEntity


    @FormUrlEncoded
    @POST(login)
    suspend fun getLogin(): LoginEntity

//    @GET("api/users")
//    suspend fun getListData(@Query("page") pageNumber: Int): PaggingEntity


    @Multipart
    @POST("imageupload")
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

//          var  keyStore = readKeyStore()
//          var  keyStore = readKeyStore()
//
//            var trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore)
//            val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
//            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
//                "Unexpected default trust managers:" + Arrays.toString(
//                    trustManagers
//                )
//            }
//            val trustManager = trustManagers[0] as X509TrustManager
//            var sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, arrayOf(trustManager), null);
//            val sslSocketFactory = sslContext.socketFactory

//            var keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(keyStore, "keystore_pass".toCharArray());
//            sslContext.init(keyManagerFactory.getKeyManagers(),arrayOf(trustManager),  SecureRandom());

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
//                .baseUrl(BuildConfig.BASEURL)
                .baseUrl("https://ataljalapi.cloud.kreatetechnologies.com/api/upload/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiCall::class.java)
        }
    }

    //    ================================

}
