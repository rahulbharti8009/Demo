package com.dummyproject.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dummyproject.R
import com.dummyproject.custom_view.otp.OnOtpCompletionListener

class MainActivity : AppCompatActivity(), View.OnClickListener , OnOtpCompletionListener {
    private var validateButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
        setListeners()
    }

    override fun onClick(v: View) {

    }

    private fun initializeUi() {

    }

    private fun setListeners() {

    }

    override fun onOtpCompleted(otp: String) {
        // do Stuff
        Toast.makeText(this, "OnOtpCompletionListener called is ==>> $otp", Toast.LENGTH_SHORT).show()
    }
}