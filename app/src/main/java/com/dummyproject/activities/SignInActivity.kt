package com.dummyproject.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.net.ConnectivityManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dummyproject.R
import com.dummyproject.apis.Status
import com.dummyproject.databinding.ActivitySignInBinding
import com.dummyproject.utils.*
import com.dummyproject.viewmodel.LoginViewModel
import okhttp3.internal.wait

class SignInActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding : ActivitySignInBinding

    private val viewModel: LoginViewModel by viewModels {
        InjectorUtils.provideLoginViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        listener()

        if(isNetworkAvailable(this))
          getData()
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
                                snackbar(this, it)
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

    override  fun initialized() {
    }
    override  fun listener() {
        binding.btnVerify.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnVerify ->{
               startActivity(Intent(this , OtpActivity::class.java))
            }
            else ->{}}
    }

}