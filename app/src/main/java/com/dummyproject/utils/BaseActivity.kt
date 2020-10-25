package com.dummyproject.utils

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

abstract class BaseActivity  : AppCompatActivity() {
     lateinit var auth: FirebaseAuth
     lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

  abstract fun initialized()
  abstract fun listener()

 }