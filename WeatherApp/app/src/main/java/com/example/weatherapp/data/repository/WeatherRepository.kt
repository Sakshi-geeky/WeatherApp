package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.api.NetworkService
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Weather
import javax.inject.Inject

const val Apikey = "jiXVfl2xAtzzIG2F1xGa7wqETGliE06u"

class WeatherRepository @Inject constructor(
    private val networkService: NetworkService
) {
    suspend fun getCities(city : String) : List<City>? {
        val result = networkService.getCities(city,Apikey)
        if(result != null && result.isSuccessful){
            Log.d("WeatherRepository", "API result: ${result.body()}")
            return result.body()
        } else {
            Log.e("WeatherRepository", "API error: ${result.errorBody()?.string()}")
        }
        return null
    }

    suspend fun getWeather(url : String) : Weather? {
        val result = networkService.getWeather(url,Apikey)
        if(result != null && result.isSuccessful){
            return result.body()
        }
        return null
    }
}

