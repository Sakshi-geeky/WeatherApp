package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface NetworkService {
    @GET("locations/v1/cities/search")
    suspend fun getCities(
        @Query("q") city : String,
        @Query("apikey") apikey : String
    ) : Response<List<City>>

    @GET("forecasts/v1/daily/5day/{locationKey}")
    suspend fun getWeather(
        @Path("locationKey") locationKey : String,
        @Query("apikey") apikey : String
    ) : Response<Weather>

}