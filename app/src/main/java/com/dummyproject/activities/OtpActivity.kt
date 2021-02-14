package com.dummyproject.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dummyproject.R
import com.dummyproject.custom_view.otp.OnOtpCompletionListener
import com.dummyproject.databinding.ActivityOtpBinding
import com.dummyproject.utils.BaseActivity
import com.dummyproject.utils.hideKeyboard
import com.dummyproject.utils.snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpActivity : BaseActivity() , View.OnClickListener , OnOtpCompletionListener{

    var TAG = OtpActivity::class.java.name
    private lateinit var etArray: Array<EditText>
    lateinit var binding: ActivityOtpBinding

    private var storedVerificationId: String? = ""

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
        auth = FirebaseAuth.getInstance()
        storedVerificationId = intent.getStringExtra("storedVerificationId")
        binding.btnOtpVerify.isEnabled = false

        val word: Spannable = SpannableString(resources.getString(R.string.str_don_t_receive_the_otp_resend_otp))
        word.setSpan(ForegroundColorSpan(Color.RED), word.length - 10, word.length, 0)
        binding.tvResendOtp.setText(word, TextView.BufferType.SPANNABLE)

        val number = "+91 ${intent.getStringExtra("mobile")}"
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

            }
            else ->{}
        }
    }

    override fun onOtpCompleted(otp: String?) {
        // do Stuff
        hideKeyboard(this)
        binding.btnOtpVerify.isEnabled = true
        binding.btnOtpVerify.backgroundTintList = ContextCompat.getColorStateList(this , R.color.red)
//            verifyPhoneNumberWithCode(storedVerificationId, otp!!)

        if(otp.equals("123456")){
            snackbar(this , "Otp is success ==>> $otp")
//            Log.d(TAG , "storedVerificationId $storedVerificationId OTP $otp")
//            verifyPhoneNumberWithCode(storedVerificationId, otp!!)
            startActivity(Intent(this, HomeControllerActivity::class.java))
        }
        else{
            snackbar(this , "Please enter otp 1234")
        }
    }


    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    preferenceUtil.login = true
                    startActivity(Intent(this, HomeControllerActivity::class.java))

                } else {
                    snackbar(this , "Please enter otp 1234")
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        snackbar(applicationContext , "Invalid code.")
                    }
                }
            }
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks, // OnVerificationStateChangedCallbacks
            token) // ForceResendingToken from callbacks
    }
}