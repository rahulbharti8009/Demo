package com.dummyproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dummyproject.R
import com.dummyproject.activities.AddMoneyActivity
import com.dummyproject.adapter.LastWeakAdapter
import com.dummyproject.adapter.OlderAdapter
import com.dummyproject.adapter.RecentCallAdapter
import com.dummyproject.databinding.FragmentHomeControllerBinding
import com.dummyproject.utils.PeekingLinearLayoutManager
import com.dummyproject.utils.RVPagerSnapFancyDecorator
import com.dummyproject.utils.StartSnapHelper

class HomeControllerFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentHomeControllerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    companion object {
        var instance: HomeControllerFragment? = null
        @JvmStatic
        fun newInstance(): HomeControllerFragment {
            if (instance == null)
                instance = HomeControllerFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_controller, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialized()
        listener()
    }

    private fun initialized() {
        binding.rvLastWeak.layoutManager = PeekingLinearLayoutManager(context, 0.48f)

        //TODO () ==> (Last Weak) set dummy content
        val weakList: MutableList<String> = mutableListOf()
        for (i in 0 until 10) (weakList.add("item $i"))
        val adapter = LastWeakAdapter(context!!, weakList)
        binding.rvLastWeak.adapter = adapter
        adapter.notifyDataSetChanged()

        val cardWidthPixelsLasrWEak = this.resources.displayMetrics.widthPixels * 0.95f
        val cardHintPercentLasrWEak = 0.01f
        binding.rvLastWeak.addItemDecoration(
            RVPagerSnapFancyDecorator(
                context!!,
                cardWidthPixelsLasrWEak,
                cardHintPercentLasrWEak
            )
        )
        val startSnapHelperLasrWEak = StartSnapHelper()
        startSnapHelperLasrWEak.attachToRecyclerView(binding.rvLastWeak)
//-----------------------------------------------------------------------
        //TODO () ====>> (Older) set dummy content
        binding.rvOlder.layoutManager = PeekingLinearLayoutManager(context, 0.48f)
        val older: MutableList<String> = mutableListOf()
        for (i in 0 until 10) (older.add("item $i"))
        val adapterOlder = OlderAdapter(context!!, older)
        binding.rvOlder.adapter = adapterOlder
        adapterOlder.notifyDataSetChanged()

        val cardWidthPixels = this.resources.displayMetrics.widthPixels * 0.95f
        val cardHintPercent = 0.01f
        binding.rvOlder.addItemDecoration(
            RVPagerSnapFancyDecorator(
                context!!,
                cardWidthPixels,
                cardHintPercent
            )
        );
        val startSnapHelper = StartSnapHelper()
        startSnapHelper.attachToRecyclerView(binding.rvOlder)
//-----------------------------------------------------------------------

        //TODO () ====>> (Recent Calls) set dummy content
        binding.rvRecentCall.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recentCall: MutableList<String> = mutableListOf()
        for (i in 0 until 10) (recentCall.add("item $i"))
        val adapterRecent = RecentCallAdapter(context!!, recentCall)
        binding.rvRecentCall.adapter = adapterRecent
        adapterRecent.notifyDataSetChanged()
    }

    private fun listener() {
        binding.llytAddFund.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llytAddFund -> {
                startActivity(Intent(context, AddMoneyActivity::class.java))
            }
            else -> {
            }
        }
    }
}