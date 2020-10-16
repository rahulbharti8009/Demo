package com.dummyproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.dummyproject.R
import com.dummyproject.utils.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpalshActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            startActivity(Intent(this@SpalshActivity, SignInActivity::class.java))
            finish()
        }
    }

    override  fun initialized() {
    }
    override  fun listener() {

    }
}