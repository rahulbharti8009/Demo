package com.dummyproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dummyproject.R
import com.dummyproject.adapter.LastWeakAdapter
import com.dummyproject.adapter.OlderAdapter
import com.dummyproject.adapter.RecentCallAdapter
import com.dummyproject.apis.Status
import com.dummyproject.databinding.FragmentHomeControllerBinding
import com.dummyproject.entity.LoginEntity
import com.dummyproject.utils.*
import com.dummyproject.viewmodel.LoginViewModel

class HomeControllerFragment : Fragment(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels {
        InjectorUtils.provideLoginViewModelFactory()
    }
    lateinit var binding: FragmentHomeControllerBinding

    var adapterRecent : RecentCallAdapter? = null
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
//        binding.rvLastWeak.layoutManager = PeekingLinearLayoutManager(context, 0.48f)
//
//        //TODO () ==> (Last Weak) set dummy content
//        val weakList: MutableList<String> = mutableListOf()
//        for (i in 0 until 10) (weakList.add("item $i"))
//        val adapter = LastWeakAdapter(context!!, weakList)
//        binding.rvLastWeak.adapter = adapter
//        adapter.notifyDataSetChanged()
//
//        val cardWidthPixelsLasrWEak = this.resources.displayMetrics.widthPixels * 0.95f
//        val cardHintPercentLasrWEak = 0.01f
//        binding.rvLastWeak.addItemDecoration(
//            RVPagerSnapFancyDecorator(
//                context!!,
//                cardWidthPixelsLasrWEak,
//                cardHintPercentLasrWEak
//            )
//        )
//        val startSnapHelperLasrWEak = StartSnapHelper()
//        startSnapHelperLasrWEak.attachToRecyclerView(binding.rvLastWeak)
//-----------------------------------------------------------------------
        //TODO () ====>> (Older) set dummy content
//        binding.rvOlder.layoutManager = PeekingLinearLayoutManager(context, 0.48f)
//        val older: MutableList<String> = mutableListOf()
//        for (i in 0 until 10) (older.add("item $i"))
//        val adapterOlder = OlderAdapter(context!!, older)
//        binding.rvOlder.adapter = adapterOlder
//        adapterOlder.notifyDataSetChanged()
//
//        val cardWidthPixels = this.resources.displayMetrics.widthPixels * 0.95f
//        val cardHintPercent = 0.01f
//        binding.rvOlder.addItemDecoration(
//            RVPagerSnapFancyDecorator(
//                context!!,
//                cardWidthPixels,
//                cardHintPercent
//            )
//        );
//        val startSnapHelper = StartSnapHelper()
//        startSnapHelper.attachToRecyclerView(binding.rvOlder)
//-----------------------------------------------------------------------
//Nitesh
        //TODO () ====>> (Recent Calls) set dummy content
        binding.rvRecentCall.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recentCall: MutableList<LoginEntity> = mutableListOf()
        adapterRecent = RecentCallAdapter(context!!, recentCall)
        binding.rvRecentCall.adapter = adapterRecent
        adapterRecent?.notifyDataSetChanged()
    }

    private fun listener() {
        getDataList()
    }

    private fun getDataList() {
        viewModel.getFetchDataList().observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            snackbar(requireActivity(), it[0].userId.toString())
                            adapterRecent?.addData(it)
                        }
                    }
                    Status.ERROR -> {
                        it?.message.let {
                            snackbar(requireActivity(), it!!)
                        }
                    }
                    Status.LOADING -> {
                        Log.e("TAG", "Loading")
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            else -> {
            }
        }
    }
}