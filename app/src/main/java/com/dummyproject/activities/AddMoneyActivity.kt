package com.dummyproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dummyproject.R
import com.dummyproject.databinding.ActivityAddMoneyBinding
import com.dummyproject.utils.BaseActivity
import kotlinx.android.synthetic.main.public_toolbar.view.*

class AddMoneyActivity : BaseActivity() , View.OnClickListener{
    lateinit var binding: ActivityAddMoneyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_money)
        initialized()
        listener()
    }

    override  fun initialized() {
        binding.include.titleToolbar.apply {

            text = getString(R.string.str_add_money)
            setTextColor(ContextCompat.getColor(this@AddMoneyActivity, R.color.white))
        }
    }
    override  fun listener() {
        binding.include.ivBack.setOnClickListener(this)
        binding.btnVerify.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.ivBack ->{
               onBackPressed()
           }
           R.id.btnVerify ->{
               startActivity(Intent(this,ChoosePayMethodActivity::class.java))
           }
           else ->{}}
    }
}