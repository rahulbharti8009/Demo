package com.dummyproject.activities

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dummyproject.R
import com.dummyproject.apis.Status
import com.dummyproject.databinding.ActivitySignInBinding
import com.dummyproject.entity.FileBitmapModel
import com.dummyproject.utils.*
import com.dummyproject.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import okhttp3.MultipartBody

import okhttp3.RequestBody
import retrofit2.http.Part
import java.io.FileInputStream
import android.app.PictureInPictureParams
import android.graphics.Point
import android.os.Build

import android.util.Rational

import android.view.Display





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
    }

    private fun getData() {
        viewModel.getFetchData().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            snackbar(this, it.title!!)
                            isOTPScreen(storedVerificationId!!)
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

    private fun getSavePicData(pic:MultipartBody.Part) {
        viewModel.getSavePic(pic).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            snackbar(this, it.status!!)
                        }
                    }
                    Status.ERROR -> {
                            snackbar(this, "it.message!!")
                    }
                    Status.LOADING -> {
                        Log.e("TAG", "Loading")
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                Constant.REQ_CODE_SPEECH_INPUT -> {
                    if (isNotNull(data) && resultCode == RESULT_OK) {
                        val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        if (result != null && result.size > 0) {

                        }
                    }
                }
                Permission.CAMERA -> {
                    val fileBitmapModel: FileBitmapModel =
                        ImageUtil.getFileBitmapOfCameraResponse(data, this)
                    if (isNotNull(fileBitmapModel)) {
                        val entity: FileBitmapModel = ImageUtil.getResizedFileBitmapModel(fileBitmapModel)
                        if (isNotNull(entity)) {
                            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), entity.file)
                            val body: MultipartBody.Part =    MultipartBody.Part.createFormData("inputfile", entity.file.getName(), requestFile)
                            getSavePicData(body)
                        }
                    }
                }
                else -> {
                }
            }
        }
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
        binding.button.setOnClickListener(this)
        binding.image.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnVerify -> {
                getData()
            }
            R.id.image ->{
                if (Permission.isCameraPermission(this)) {
            ImageUtil.takePhotoFromCamera(this)
                }
            }
            R.id.button -> {
                val width = window.decorView.width * 2
                val height = window.decorView.height
                val ratio = Rational(width, height)
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    var  pip_Builder =   PictureInPictureParams.Builder()
                    pip_Builder.setAspectRatio(ratio)
//                    .setActions(getPIPActions(getCurrentVideo())).build();
                    enterPictureInPictureMode(pip_Builder.build())
                } else { }

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