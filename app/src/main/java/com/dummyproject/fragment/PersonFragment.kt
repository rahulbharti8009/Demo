package com.dummyproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dummyproject.R
import com.dummyproject.adapter.ContactListAdapter
import com.dummyproject.databinding.FragmentContactBinding
import com.dummyproject.databinding.FragmentPersonBinding

class PersonFragment : Fragment() {

    lateinit var binding : FragmentPersonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_person, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialized()
    }

   private fun initialized(){
       //TODO () ====>> (Recent Calls) set dummy content
       binding.rvContactList.layoutManager =
           LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
       val contactList: MutableList<String> = mutableListOf()
       for (i in 0 until 10) (contactList.add("item $i"))
       val contactListAdapter = ContactListAdapter(context!!, contactList)
       binding.rvContactList.adapter = contactListAdapter
       contactListAdapter.notifyDataSetChanged()
    }

    companion object {
        var instance : PersonFragment? = null
        @JvmStatic
        fun newInstance() : PersonFragment{
            if(instance == null)
                instance = PersonFragment()
            return instance!!
        }
    }
}