package com.dummyproject.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dummyproject.R
import com.dummyproject.databinding.ActivitySignUpBinding
import com.dummyproject.utils.BaseActivity

class SignUpActivity : BaseActivity() {
    lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.apply {  }
    }

    override  fun initialized() {
    }
    override  fun listener() {

    }
}