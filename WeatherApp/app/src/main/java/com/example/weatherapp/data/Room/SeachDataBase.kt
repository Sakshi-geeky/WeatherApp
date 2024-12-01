package com.example.weatherapp.data.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchHistoryEntity::class], version = 1)
abstract class SearchDataBase : RoomDatabase() {
    abstract fun searchHistoryDao() : SearchHistoryDao
}