package com.dummyproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dummyproject.R
import com.dummyproject.entity.LoginEntity
import kotlinx.android.synthetic.main.recent_call_item.view.*

class RecentCallAdapter(var context: Context, var weakList: MutableList<LoginEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun addData( weakList: MutableList<LoginEntity>){
        this.weakList = weakList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LastWeak(context,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recent_call_item, parent, false), weakList
        )
    }


    class LastWeak(var context: Context , itemView: View,var weakList: MutableList<LoginEntity>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.findViewById<CardView>(R.id.cv).setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.cv ->{ Toast.makeText(context , weakList[absoluteAdapterPosition].message, Toast.LENGTH_LONG).show()}
                else ->{}
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tvTitle.text = weakList[position].userId
    }

    override fun getItemCount(): Int {
        return if(weakList.size > 0)  weakList.size else 0
    }


}