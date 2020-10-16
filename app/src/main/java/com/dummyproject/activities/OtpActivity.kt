package com.dummyproject.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dummyproject.R
import com.dummyproject.custom_view.otp.OnOtpCompletionListener
import com.dummyproject.databinding.ActivityOtpBinding
import com.dummyproject.utils.BaseActivity
import com.dummyproject.utils.hideKeyboard
import com.dummyproject.utils.snackbar

class OtpActivity : BaseActivity() , View.OnClickListener , OnOtpCompletionListener{

    private lateinit var etArray: Array<EditText>
    lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)
        initialized()
        listener()
    }

    override fun initialized() {
        binding.btnOtpVerify.isEnabled = false
        val word: Spannable = SpannableString(resources.getString(R.string.str_don_t_receive_the_otp_resend_otp))
        word.setSpan(ForegroundColorSpan(Color.RED), word.length - 10, word.length, 0)
        binding.tvResendOtp.setText(word, TextView.BufferType.SPANNABLE)

        val number = "+91 8787675434"
        val text =" ${resources.getString(R.string.enter_the_confirmation_code_sen_to, number)} "
        val wordNumber: Spannable = SpannableString(text)
        wordNumber.setSpan(
            ForegroundColorSpan(Color.BLACK),
            wordNumber.length - 13,
            wordNumber.length,
            0
        )
        binding.tvNumber.setText(wordNumber, TextView.BufferType.SPANNABLE)
    }

    override fun listener() {
        binding.btnOtpVerify.setOnClickListener(this)
        binding.otpView.setOtpCompletionListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnOtpVerify ->{
                startActivity(Intent(this, HomeControllerActivity::class.java))
            }
            else ->{}
        }
    }

    override fun onOtpCompleted(otp: String?) {
        // do Stuff
        hideKeyboard(this)
        binding.btnOtpVerify.isEnabled = true
        binding.btnOtpVerify.backgroundTintList = ContextCompat.getColorStateList(this , R.color.red)

        if(otp.equals("1234")){
            snackbar(this , "Otp is success ==>> $otp")
        }
        else{
            snackbar(this , "Please enter otp 1234")
        }
    }
}