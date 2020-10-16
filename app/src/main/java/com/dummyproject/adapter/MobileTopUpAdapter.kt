package com.dummyproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dummyproject.R
import com.dummyproject.activities.HomeControllerActivity
import com.dummyproject.databinding.ContactListItemBinding
import kotlinx.android.synthetic.main.contact_list_item.view.*

class MobileTopUpAdapter( var context: Context, var weakList: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding : ContactListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.contact_list_item,parent,false);
        return  LastWeak(binding.root, context!!)
    }

    class LastWeak(itemView: View, var context : Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.findViewById<RelativeLayout>(R.id.rlyContain).setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.rlyContain ->{
//                    context .startActivity(Intent(context ,  ChooseTopUpActivity::class.java))
                val invoked =  context as HomeControllerActivity
                    invoked. mobileTop()
                }
                else ->{}
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
// set Default
        holder.itemView.tvInvite.visibility = View.GONE
        holder.itemView.ivCall.visibility = View.GONE
        if(position != 0)
            holder.itemView.tvCharacterWise.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return weakList.size
    }


}