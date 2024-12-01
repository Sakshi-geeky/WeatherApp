package com.example.weatherapp.data.di

import com.example.weatherapp.data.api.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltApiObject {
    @Provides
    @Singleton
    fun buildRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dataservice.accuweather.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun createRetrofit(retrofit: Retrofit) : NetworkService {
        return retrofit.create(NetworkService::class.java)
    }
}