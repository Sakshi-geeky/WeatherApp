package com.example.weatherapp.data.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchText(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT * FROM search_history")
    suspend fun getSearchText() : List<SearchHistoryEntity>

    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()
}