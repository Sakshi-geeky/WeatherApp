package com.example.weatherapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.Room.SearchHistoryDao
import com.example.weatherapp.data.Room.SearchHistoryEntity
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val searchHistoryDao: SearchHistoryDao
) : ViewModel() {

    private val _citiesList : MutableLiveData<List<City>> = MutableLiveData(emptyList())
    val citiesList : LiveData<List<City>> = _citiesList

    private val _apiError : MutableLiveData<String> = MutableLiveData("")
    val apiError : LiveData<String> = _apiError

    private val _cityWeather : MutableLiveData<Weather> = MutableLiveData(null)
    val cityWeather : LiveData<Weather> = _cityWeather

    private val _searchedTexts : MutableLiveData<List<SearchHistoryEntity>> = MutableLiveData(emptyList())
    val searchedTexts : LiveData<List<SearchHistoryEntity>> = _searchedTexts

    private val _showLoader : MutableLiveData<Boolean> = MutableLiveData(false)
    val showLoader : LiveData<Boolean> = _showLoader


//    fun getCities(city : String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = weatherRepository.getCities(city)
//            if(!result.isNullOrEmpty()) {
//                withContext(Dispatchers.Main){
//                    Log.d("WeatherViewModel", "API result: ${result}")
//                    _citiesList.value = result
//                }
//            }
//        }
//    }

//    fun getWeather(cityKey : String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = weatherRepository.getWeather(cityKey)
//            if(result != null) {
//                withContext(Dispatchers.Main){
//                    _cityWeather.value = result
//                }
//            }
//        }
//    }

//    fun clearCitiesList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _citiesList.postValue(emptyList())
//        }
//    }

//    fun insertSearchedText(text : String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            searchHistoryDao.insertSearchText(SearchHistoryEntity(searchString = text))
//        }
//    }

//    fun getSearchedTexts() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _searchedTexts.postValue(searchHistoryDao.getSearchText())
//        }
//    }

//    fun clearSearchHistory() {
//        viewModelScope.launch(Dispatchers.IO) {
//            searchHistoryDao.clearSearchHistory()
//            _searchedTexts.postValue(emptyList())
//        }
//    }

    fun getCities(city: String) {
        viewModelScope.launch {
            _showLoader.postValue(true) // Start loader
            val result = withContext(Dispatchers.IO) {
                weatherRepository.getCities(city)
            }
            _showLoader.postValue(false) // Stop loader
            if (!result.isNullOrEmpty()) {
                _citiesList.value = result
                Log.d("WeatherViewModel", "API result: $result")
            }
        }
    }

    fun getWeather(cityKey: String) {
        viewModelScope.launch {
            _showLoader.postValue(true) // Start loader
            val result = withContext(Dispatchers.IO) {
                weatherRepository.getWeather(cityKey)
            }
            _showLoader.postValue(false) // Stop loader
            if (result != null) {
                _cityWeather.value = result
            }
        }
    }

    fun clearCitiesList() {
        viewModelScope.launch {
            _showLoader.postValue(true) // Start loader
            withContext(Dispatchers.IO) {
                _citiesList.postValue(emptyList())
            }
            _showLoader.postValue(false) // Stop loader
        }
    }

    fun insertSearchedText(text: String) {
        viewModelScope.launch {
            _showLoader.postValue(true) // Start loader
            withContext(Dispatchers.IO) {
                searchHistoryDao.insertSearchText(SearchHistoryEntity(searchString = text))
            }
            _showLoader.postValue(false) // Stop loader
        }
    }

    fun getSearchedTexts() {
        viewModelScope.launch {
            _showLoader.postValue(true) // Start loader
            val texts = withContext(Dispatchers.IO) {
                searchHistoryDao.getSearchText()
            }
            _searchedTexts.postValue(texts)
            _showLoader.postValue(false) // Stop loader
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            _showLoader.postValue(true) // Start loader
            withContext(Dispatchers.IO) {
                searchHistoryDao.clearSearchHistory()
                _searchedTexts.postValue(emptyList())
            }
            _showLoader.postValue(false) // Stop loader
        }
    }
}