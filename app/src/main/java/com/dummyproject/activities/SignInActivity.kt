package com.dummyproject.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dummyproject.R
import com.dummyproject.apis.Status
import com.dummyproject.databinding.ActivitySignInBinding
import com.dummyproject.utils.*
import com.dummyproject.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class SignInActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding : ActivitySignInBinding
    val TIME_OUT = 60
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""


    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private val viewModel: LoginViewModel by viewModels {
        InjectorUtils.provideLoginViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        initialized()
        listener()

        if(isNetworkAvailable(this)){
          getData()
        }
        else
            snackbar(this, "Sorry! No network available.")

    }

    private fun getData(){
        viewModel.getFetchData().observe(this, Observer {
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS ->{
//                        Log.e("TAG","Succes : ${it.message}")
                        it.data?.let {
                            it.title?.let {
                                snackbar(this, "API Invoked ==> $it")
                            }
                        }
                    }
                    Status.ERROR ->{
                        it?.message.let {
                        snackbar(this, it!!)
                        }

                    }
                    Status.LOADING ->{
                        Log.e("TAG","Loading")
                    }
                }
            }
        })
    }

    fun isOTPScreen(storedVerificationId : String){
        val intent =  Intent(this , OtpActivity::class.java)
        intent.putExtra("mobile",binding.etNumber.text.toString())
        intent.putExtra("storedVerificationId" , storedVerificationId)
        startActivity(intent)
    }

    override  fun initialized() {
        auth = FirebaseAuth.getInstance()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                isOTPScreen(storedVerificationId!!)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)
                verificationInProgress = false

                if (e is FirebaseAuthInvalidCredentialsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                        Snackbar.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                        Snackbar.LENGTH_SHORT).show()
                }
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }
    override  fun listener() {
        binding.btnVerify.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnVerify ->{
//                startPhoneNumberVerification("+91${binding.etNumber.text.toString()}")
                isOTPScreen(storedVerificationId!!)
            }
            else ->{}}
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}