package com.dummyproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dummyproject.R
import com.dummyproject.adapter.CouponPromoAdapter
import com.dummyproject.databinding.FragmentContactBinding
import com.dummyproject.databinding.FragmentPersonBinding
import com.dummyproject.utils.RVPagerSnapFancyDecorator
import com.dummyproject.utils.StartSnapHelper

class ContactFragment : Fragment() {

    lateinit var binding : FragmentContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //TODO () ====>> (Recent Calls) set dummy content
        binding.rvCouponPromo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val couponPromo: MutableList<String> = mutableListOf()
        for (i in 0 until 10) (couponPromo.add("item $i"))
        val adapterRecent = CouponPromoAdapter(context!!, couponPromo)
        binding.rvCouponPromo.adapter = adapterRecent
        adapterRecent.notifyDataSetChanged()

        val cardWidthPixels = this.resources.displayMetrics.widthPixels * 0.95f
        val cardHintPercent = 0.01f
        binding.rvCouponPromo.addItemDecoration(RVPagerSnapFancyDecorator(context!!, cardWidthPixels, cardHintPercent))
        val startSnapHelper = StartSnapHelper()
        startSnapHelper.attachToRecyclerView( binding.rvCouponPromo)
    }
    companion object {
        var instance : ContactFragment? = null
        @JvmStatic
        fun newInstance() : ContactFragment{
            if(instance == null)
                instance = ContactFragment()
            return instance!!
        }
    }
}