package com.practice.roomdatabasepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.roomdatabasepractice.databinding.ActivityMainBinding
import com.practice.roomdatabasepractice.db.Subscriber
import com.practice.roomdatabasepractice.db.SubscriberDatabase
import com.practice.roomdatabasepractice.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var adapter: MyRecyclerVeiwAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val subscriberDAO = SubscriberDatabase.getInstance(this).subscriberDAO
        val subscriberRepository = SubscriberRepository(subscriberDAO)
        val viewModelFactory = MainActivityViewModelFactory(subscriberRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.events.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it,Toast.LENGTH_SHORT).show()
            }
        })
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter = MyRecyclerVeiwAdapter({selectedItem:Subscriber->listItemClicked(selectedItem)})
        binding.subscriberRecyclerview.adapter = adapter
        displaySubscribersList()
    }

    fun displaySubscribersList(){
        viewModel.subscribers.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    fun listItemClicked(subscriber: Subscriber){

        viewModel.initUpdateAndDelete(subscriber)

        //Toast.makeText(this,subscriber.email,Toast.LENGTH_SHORT).show()
    }
}