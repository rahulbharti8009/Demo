package com.dummyproject.entity

import com.google.gson.annotations.SerializedName

class LoginEntity {
     val data: Data? = null
     val status: String? = null
     val message: String? = null
     val userId: String? = null
     val title: String? = null
     val id: String? = null
     val otp: Int? = null
     val completed: Boolean?  = null
//     val status: Int? = 0
 }

 class Data {
     val token: String? = null
     val userDetails: UserDetails? = null
    val call_priority: Int? = null
    val country_code: Any? = null
    val created_at: String? = null
    val deviceToken: String? = null
    val dob: String? = null
    val email: String? = null
    val full_name: String? = null
    val image: String? = null
    val language_code: String? = null
    val mobile: String? = null
    val status: Int?  = null
    val updated_at: String? = null
    val user_code: String? = null
    val user_id: Int? =  null
}

data class UserDetails(
    val _id: String,
    val countrycode: Int,
    val deviceid: String,
    val devicename: String,
    val email: String,
    val mobile: String,
    val name: String
)