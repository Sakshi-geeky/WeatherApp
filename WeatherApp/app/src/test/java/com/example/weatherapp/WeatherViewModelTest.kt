package com.example.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weatherapp.data.Room.SearchHistoryDao
import com.example.weatherapp.data.Room.SearchHistoryEntity
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    // Use this rule to handle LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherViewModel: WeatherViewModel

    // Mock the repository and DAO
    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var searchHistoryDao: SearchHistoryDao

    // Observers to observe LiveData changes
    @Mock
    private lateinit var citiesObserver: Observer<List<City>>

    @Mock
    private lateinit var weatherObserver: Observer<Weather>

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherViewModel = WeatherViewModel(weatherRepository, searchHistoryDao)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getCities updates citiesList when successful`() = runBlockingTest {
        val cityName = "London"
        val citiesList = listOf(City(englishName = "London"))

        // Mock the result of the weatherRepository.getCities()
        `when`(weatherRepository.getCities(cityName)).thenReturn(citiesList)

        // Observe the LiveData
        weatherViewModel.citiesList.observeForever(citiesObserver)

        // Call the function to be tested
        weatherViewModel.getCities(cityName)

        // Verify the LiveData update
        verify(citiesObserver).onChanged(citiesList)
    }

    @Test
    fun `getWeather updates cityWeather when successful`() = runBlockingTest {
        val cityKey = "123"
        val weather = Weather()

        `when`(weatherRepository.getWeather(cityKey)).thenReturn(weather)

        weatherViewModel.cityWeather.observeForever(weatherObserver)
        weatherViewModel.getWeather(cityKey)

        verify(weatherObserver).onChanged(weather)
    }

    @Test
    fun `clearCitiesList should empty citiesList`() = runBlockingTest {
        weatherViewModel.citiesList.observeForever(citiesObserver)

        weatherViewModel.clearCitiesList()

        verify(citiesObserver).onChanged(emptyList())
    }

    @Test
    fun `insertSearchedText inserts text into database`() = runBlockingTest {
        val searchText = "London"

        weatherViewModel.insertSearchedText(searchText)

        verify(searchHistoryDao).insertSearchText(SearchHistoryEntity(searchString = searchText))
    }

    @Test
    fun `getSearchedTexts updates searchedTexts`() = runBlockingTest {
        val searchHistory = listOf(SearchHistoryEntity(searchString = "London"))

        `when`(searchHistoryDao.getSearchText()).thenReturn(searchHistory)

        weatherViewModel.searchedTexts.observeForever {  }
        weatherViewModel.getSearchedTexts()

        assert(weatherViewModel.searchedTexts.value == searchHistory)
    }

    @Test
    fun `clearSearchHistory should empty searchedTexts`() = runBlockingTest {
        weatherViewModel.searchedTexts.observeForever { }

        weatherViewModel.clearSearchHistory()

        assert(weatherViewModel.searchedTexts.value == emptyList<SearchHistoryEntity>())
    }
}