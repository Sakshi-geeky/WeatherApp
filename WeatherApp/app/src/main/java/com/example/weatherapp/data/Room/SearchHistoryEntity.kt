package com.example.weatherapp.data.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey val id : Int? = null,
    val searchString : String
)