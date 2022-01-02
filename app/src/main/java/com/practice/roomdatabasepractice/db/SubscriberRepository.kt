package com.practice.roomdatabasepractice.db

class SubscriberRepository(val dao : SubscriberDAO) {
    val subscriber = dao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber): Long{
       return dao.insertData(subscriber)
    }
    suspend fun update(subscriber: Subscriber){
        dao.updateData(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        dao.deleteData(subscriber)
    }
    suspend fun deleteAll(){
        dao.deleteAll()
    }
}