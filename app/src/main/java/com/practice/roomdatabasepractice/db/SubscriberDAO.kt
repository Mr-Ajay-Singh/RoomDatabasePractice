package com.practice.roomdatabasepractice.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(subscriber: Subscriber) : Long

    @Insert
    suspend fun insertData(subscriber: Subscriber,subsList : List<Subscriber>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateData(subscriber: Subscriber)

    @Delete
    suspend fun deleteData(subscriber: Subscriber)

    @Query(value = "DELETE  FROM subscriber_data_table")
    suspend fun deleteAll()

    //as live data do already in background thats why we dont need suspend modifier
    @Query(value = "SELECT *  FROM subscriber_data_table")
    fun getAllSubscribers() : LiveData<List<Subscriber>>

}