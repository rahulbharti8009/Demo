package com.dummyproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dummyproject.R
import com.dummyproject.databinding.CustomVideoItemBinding
import com.dummyproject.entity.Video
import kotlinx.android.synthetic.main.custom_video_item.view.*


class VideoAdapter(var context: Context, var mVedioList: MutableList<Video>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: CustomVideoItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.custom_video_item,
            parent,
            false
        );
        return VideoItem(binding.root, context)
    }

    class VideoItem(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        override fun onClick(v: View?) {
            when (v?.id) {

                else -> {
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/${mVedioList[position].thumb}")
        Log.d("TAG", mVedioList[position].sources[0])
        Glide.with(context)
            .load("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/${mVedioList[position].thumb}")
            .into(holder.itemView.ivThumb)
        holder.itemView.tvText.text = mVedioList[position].subtitle
    }

    override fun getItemCount(): Int {
        return mVedioList.size
    }


}