package com.practice.roomdatabasepractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.roomdatabasepractice.db.Subscriber
import com.practice.roomdatabasepractice.db.SubscriberRepository
import com.practice.roomdatabasepractice.generated.callback.OnClickListener

class MainActivityViewModelFactory(private val repository: SubscriberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(repository) as T
        }
        throw IllegalAccessException("Not Available")
    }
}