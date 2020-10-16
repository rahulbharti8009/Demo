package com.dummyproject.activities

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dummyproject.R
import com.dummyproject.databinding.ActivityProfileBinding
import com.dummyproject.utils.BaseActivity
import kotlinx.android.synthetic.main.public_toolbar.view.*

class ProfileActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityProfileBinding
    var edit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        initialized()
        listener()
        isEdit()
    }

    private fun  isEdit(){
        if(edit){
            binding.tvName.isEnabled = true
            binding.tvMobileNo.isEnabled = true
            binding.tvEmail.isEnabled = true
            binding.tvBirthday.isEnabled = true
            binding.ivArrow.visibility = View.VISIBLE

            binding.incld.titleToolbar.apply {
                text = context.getString(R.string.str_edit_profile)
                setTextColor(ContextCompat.getColor(this@ProfileActivity, R.color.white))

            }
            edit = false
            binding.incld.ivEditProfile.setBackgroundResource(R.drawable.icon_right)

        }else{
            binding.tvName.isEnabled = false
            binding.tvMobileNo.isEnabled = false
            binding.tvEmail.isEnabled = false
            binding.tvBirthday.isEnabled = false
            binding.ivArrow.visibility = View.GONE

            binding.incld.ivEditProfile.setBackgroundResource(R.drawable.icon_edit)


            binding.incld.titleToolbar.apply {
                text = context.getString(R.string.str_profile)
                setTextColor(ContextCompat.getColor(this@ProfileActivity, R.color.white))
            }
            edit = true
        }
    }

    override fun initialized() {
       binding.incld.ivEditProfile.visibility = View.VISIBLE
    }

    override fun listener() {
      binding.incld.ivBack.setOnClickListener(this)
      binding.incld.ivEditProfile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      when(v?.id){
          R.id.ivBack ->{
           finish()
          }
          R.id.ivEditProfile -> {
              isEdit()
          }
          else ->{}}
    }
}