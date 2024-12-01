package com.example.weatherapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.Room.SearchDataBase
import com.example.weatherapp.data.Room.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltRoomObject {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : SearchDataBase {
        return Room.databaseBuilder(
            context,
            SearchDataBase::class.java,
            "weather_search_db"
        ).build()
    }

    @Provides
    fun provideSearchHistoryDao(dataBase: SearchDataBase) : SearchHistoryDao {
        return dataBase.searchHistoryDao()
    }
}