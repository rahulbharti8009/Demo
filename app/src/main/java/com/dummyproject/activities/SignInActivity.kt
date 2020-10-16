package com.dummyproject.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dummyproject.utils.InjectorUtils
import com.dummyproject.R
import com.dummyproject.apis.Status
import com.dummyproject.databinding.ActivitySignInBinding
import com.dummyproject.utils.BaseActivity
import com.dummyproject.utils.showToast
import com.dummyproject.viewmodel.LoginViewModel

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

    }

    private fun getData(mobile : String){
        viewModel.getLoginData(mobile, "1234").observe(this, Observer {
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS ->{
//                        Log.e("TAG","Succes : ${it.message}")
                    }
                    Status.ERROR ->{
//                        Log.e("TAG","Error : ${it.message}")

                    }
                    Status.LOADING ->{
//                        Log.e("TAG","Loading")

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
                if(binding.etNumber.text.toString().trim().isEmpty()){
                    showToast(this , "Please enter mobile number")
                    return
                }
                getData(binding.etNumber.text.toString().trim())
            }
            else ->{}}
    }

}