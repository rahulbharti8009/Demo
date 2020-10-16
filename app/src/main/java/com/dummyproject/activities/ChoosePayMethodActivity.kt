package com.dummyproject.activities

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dummyproject.R
import com.dummyproject.databinding.ActivityChoosePayMethodBinding
import com.dummyproject.utils.BaseActivity
import kotlinx.android.synthetic.main.public_toolbar.view.*

class ChoosePayMethodActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityChoosePayMethodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_pay_method)

        initialized()
        listener()
    }

    override fun initialized() {
        binding.include.titleToolbar.apply {
            text = getString(R.string.choose_payment)
            setTextColor(ContextCompat.getColor(this@ChoosePayMethodActivity, R.color.white))
        }
    }

    override fun listener() {
        binding.include.ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> {
                onBackPressed()
            }
            else -> {
            }
        }
    }
}