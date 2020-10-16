package com.dummyproject.activities

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dummyproject.R
import com.dummyproject.databinding.ActivityPaidCallPriorityBinding
import com.dummyproject.utils.BaseActivity
import kotlinx.android.synthetic.main.public_toolbar.view.*

class PaidCallPriorityActivity : BaseActivity() , View.OnClickListener{

    lateinit var binding : ActivityPaidCallPriorityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_paid_call_priority)
        initialized()
        listener()
    }

    override fun initialized() {
        binding.include.titleToolbar.apply {
            text = context.getString(R.string.str_paid_call_priority)
            setTextColor(ContextCompat.getColor(this@PaidCallPriorityActivity, R.color.white))
        }
    }

    override fun listener() {
        binding.include.ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBack ->{
                onBackPressed()
            }
            else ->{}}
    }
}