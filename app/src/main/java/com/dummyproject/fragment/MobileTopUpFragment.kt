package com.dummyproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dummyproject.R
import com.dummyproject.adapter.MobileTopUpAdapter
import com.dummyproject.databinding.FragmentMobileTopUpBinding

class MobileTopUpFragment : Fragment() {

    lateinit var binding : FragmentMobileTopUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mobile_top_up, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialized()
    }

    private fun initialized(){
        //TODO () ====>> (Recent Calls) set dummy content
        binding.rvMobileTopUp.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val contactList: MutableList<String> = mutableListOf()
        for (i in 0 until 10) (contactList.add("item $i"))
        val contactListAdapter = MobileTopUpAdapter( context!!, contactList)
        binding.rvMobileTopUp.adapter = contactListAdapter
        contactListAdapter.notifyDataSetChanged()
    }
    companion object {
        var instance : MobileTopUpFragment? = null
        @JvmStatic
        fun newInstance() : MobileTopUpFragment{
            if(instance == null)
                instance = MobileTopUpFragment()
            return instance!!
        }
    }
}