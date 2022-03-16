package com.dummyproject.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dummyproject.db.SharedPreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

abstract class BaseActivity  : AppCompatActivity() {
     lateinit var auth: FirebaseAuth
     lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

  abstract fun initialized()
  abstract fun listener()
  companion object{

  lateinit var preferenceUtil: SharedPreferenceUtil
  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceUtil =  SharedPreferenceUtil.getInstance(this)
    }


 }