package com.dummyproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dummyproject.R
import com.dummyproject.adapter.MobileCouponAdapter
import com.dummyproject.databinding.ActivityChooseTopUpBinding
import com.dummyproject.utils.RVPagerSnapFancyDecorator
import com.dummyproject.utils.StartSnapHelper

class ChooseTopUpFragment : Fragment(),  View.OnClickListener {

    lateinit var binding : ActivityChooseTopUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_choose_top_up, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialized()
        listener()
    }

    fun initialized() {
//
//        binding.include.titleToolbar.apply {
//            text = "Mobile Top-Up"
//            setTextColor(ContextCompat.getColor(context, R.color.white))
//        }

        //TODO () ====>> (Coupon List) set dummy content
        binding.rvCouponList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val couponList: MutableList<String> = mutableListOf()
        for (i in 0 until 10) (couponList.add("item $i"))
        val adapterRecent = MobileCouponAdapter(context!!, couponList)
        binding.rvCouponList.adapter = adapterRecent
        adapterRecent.notifyDataSetChanged()

        val cardWidthPixels = this.resources.displayMetrics.widthPixels * 0.95f
        val cardHintPercent = 0.01f
        binding.rvCouponList.addItemDecoration(RVPagerSnapFancyDecorator(context!!, cardWidthPixels, cardHintPercent))
        val startSnapHelper = StartSnapHelper()
        startSnapHelper.attachToRecyclerView( binding.rvCouponList)
    }


    fun listener() {
//        binding.include.ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
    }


}