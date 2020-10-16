package com.dummyproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dummyproject.R
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListAdapter(var context: Context, var weakList: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LastWeak(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_list_item, parent, false)
        )
    }


    class LastWeak(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position != 0)
            holder.itemView.tvCharacterWise.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return weakList.size
    }


}