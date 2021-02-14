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
import com.dummyproject.utils.BaseActivity
import com.dummyproject.utils.InjectorUtils
import com.dummyproject.utils.isNetworkAvailable
import com.dummyproject.utils.snackbar
import com.dummyproject.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class SignInActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivitySignInBinding
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
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        initialized()
        listener()
        getData()

    }

    private fun getData() {
        viewModel.getFetchData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            Log.e("TAG","Succes : ${it.title}")
                            it.title?.let {
                                snackbar(this, "API Invoked ==> $it")
                            }
                        }
                    }
                    Status.ERROR -> {
                        it?.message.let {
                            snackbar(this, it!!)
                        }

                    }
                    Status.LOADING -> {
                        Log.e("TAG", "Loading")
                    }
                }
            }
        })
    }

    fun isOTPScreen(storedVerificationId: String) {
        val intent = Intent(this, OtpActivity::class.java)
        intent.putExtra("mobile", binding.etNumber.text.toString())
        intent.putExtra("storedVerificationId", storedVerificationId)
        startActivity(intent)
    }

    override fun initialized() {
        auth = FirebaseAuth.getInstance()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                binding.ProgressBar.visibility = View.GONE
                isOTPScreen(storedVerificationId!!)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, "onVerificationFailed", e)
                binding.ProgressBar.visibility = View.GONE
                verificationInProgress = false
                if (e is FirebaseAuthInvalidCredentialsException) {
                   snackbar(applicationContext, "Quota exceeded")
                } else if (e is FirebaseTooManyRequestsException) {
                    snackbar(applicationContext, "Quota exceeded")
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId token $token")
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    override fun listener() {
        binding.btnVerify.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnVerify -> {
                binding.ProgressBar.visibility = View.VISIBLE
//                if (isNetworkAvailable(this)) {
//                    startPhoneNumberVerification("+91${binding.etNumber.text.toString()}")
//                } else
//                    snackbar(this, "Sorry! No network available.")
                isOTPScreen(storedVerificationId!!)
            }
            else -> { }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}