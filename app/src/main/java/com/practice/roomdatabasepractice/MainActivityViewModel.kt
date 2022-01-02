package com.practice.roomdatabasepractice

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.roomdatabasepractice.db.Subscriber
import com.practice.roomdatabasepractice.db.SubscriberRepository
import com.practice.roomdatabasepractice.generated.callback.OnClickListener
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repository: SubscriberRepository
) : ViewModel(), Observable {

    val subscribers = repository.subscriber
    var updateAndDelete = false
    lateinit var subscriberToUpdateAndDelete: Subscriber
     var  events = MutableLiveData<Event<String>>()

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (updateAndDelete) {
            update(subscriberToUpdateAndDelete)
            events.value = Event("Data Updated")
        } else {
            val name: String = inputName.value!!
            val email: String = inputEmail.value!!
            val resultOutput = insert(Subscriber(0, name, email))
            inputName.value = null
            inputEmail.value = null
            events.value = Event("Data Inserted +${resultOutput}")
        }
    }

    fun deleteData(){
        if(updateAndDelete) {
            inputName.value = null
            inputEmail.value = null
            updateAndDelete = !updateAndDelete
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "ClearAll"
            delete(subscriberToUpdateAndDelete)
        }else{
            deleteAll()
        }
        events.value = Event("Data Deleted")
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {

        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        subscriberToUpdateAndDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"

    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch {
             val res = repository.insert(subscriber)
             events.value = Event("Data inserted ${res}")
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.update(subscriber)
        }
        inputName.value = null
        inputEmail.value = null
        updateAndDelete = !updateAndDelete
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}