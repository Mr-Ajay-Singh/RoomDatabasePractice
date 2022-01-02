package com.practice.roomdatabasepractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.roomdatabasepractice.databinding.ListItemBinding
import com.practice.roomdatabasepractice.db.Subscriber

class MyRecyclerVeiwAdapter( private val clickListener: (Subscriber) -> Unit) : RecyclerView.Adapter<MyViewHolder>()  {
    var subscriberList = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent, false)
//        return MyViewHolder(view)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemBinding>(layoutInflater,R.layout.list_item,parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }

    fun setList(list : List<Subscriber>){
        subscriberList.clear()
        subscriberList.addAll(list)
    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber : Subscriber, clickListener: (Subscriber) -> Unit){
        binding.subscriberNameInList.text = subscriber.name
        binding.subscriberEmailInList.text = subscriber.email
        binding.listItemLayout.setOnClickListener{
            clickListener(subscriber)
        }
    }
}