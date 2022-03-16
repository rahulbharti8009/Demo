package com.dummyproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dummyproject.R
import com.dummyproject.adapter.VideoAdapter
import com.dummyproject.databinding.FragmentVideoBinding
import com.dummyproject.entity.VideoEntity
import com.dummyproject.utils.Constant.Companion.json
import com.google.gson.Gson

class VideoFragment : Fragment() {
    var TAG = "VideoFragment"

    lateinit var binding: FragmentVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG, "onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialized()
    }

    private fun initialized() {
        binding.rvVideo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var videoEntity = Gson().fromJson(json, VideoEntity::class.java)
        val videoAdapter = VideoAdapter(context!!, videoEntity.categories[0].videos)
        binding.rvVideo.adapter = videoAdapter
        videoAdapter.notifyDataSetChanged()
    }

    companion object {
        var instance: VideoFragment? = null

        @JvmStatic
        fun newInstance(): VideoFragment {
            if (instance == null)
                instance = VideoFragment()
            return instance!!
        }
    }
}