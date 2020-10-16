package com.dummyproject.utils

import androidx.appcompat.app.AppCompatActivity

 abstract class BaseActivity  : AppCompatActivity() {

  abstract fun initialized()
  abstract fun listener()

}