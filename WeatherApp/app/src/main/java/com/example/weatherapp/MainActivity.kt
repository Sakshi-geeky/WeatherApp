package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.DailyForecasts
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the status bar transparent
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        // Set status bar icons to dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppNavGraph()
                }
            }
        }
    }
}

@Composable
fun MyAppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            EnhancedWeatherHomeScreen(navController)
        }

        composable("detailScreen/{cityKey}") {
            val cityKey = it.arguments?.getString("cityKey")
            EnhancedDetailScreen(cityKey.orEmpty())
        }
    }
}

//@Composable
//fun WeatherUi(
//    navHostController: NavHostController,
//    weatherViewModel: WeatherViewModel = hiltViewModel()
//) {
//    val cittiesList = weatherViewModel.citiesList.observeAsState()
//    val searchTextList = weatherViewModel.searchedTexts.observeAsState()
//
//    val text = remember { mutableStateOf("") }
//
//    val focusRequester = remember { FocusRequester() }
//
//    var hasFocus by remember{ mutableStateOf(false) }
//
//    LaunchedEffect(key1 = Unit){
//        focusRequester.requestFocus()
//    }
//
//    LaunchedEffect(navHostController.currentBackStackEntry) {
//        weatherViewModel.getSearchedTexts()
//    }
//
//    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
//        Card(
//            modifier = Modifier
//                .focusRequester(focusRequester)
//                .padding(vertical = 12.dp)
//                .height(40.dp),
//            shape = RoundedCornerShape(8.dp),
//            border = BorderStroke(
//                width = 1.dp,
//                color = Color.Black
//            )
//        ) {
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
//                    .padding(top = 4.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                BasicTextField(
//                    modifier = Modifier
//                        .onFocusChanged { hasFocus = it.hasFocus }
//                        .weight(1f),
//                    value = text.value,
//                    onValueChange = {
//                        text.value = it
//                        weatherViewModel.getCities(it)
//                    },
//                    textStyle = TextStyle.Default,
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Done
//                    ),
//                    singleLine = true,
//                    decorationBox = { innerTextField ->
//                        Row {
//                            IconButton(onClick = { /* Handle search icon click */ }) {
//                                Icon(
//                                    imageVector = Icons.Filled.Search,
//                                    contentDescription = "Search Icon"
//                                )
//                            }
//
//                            innerTextField()
//                        }
//
//                    }
//                )
//            }
//        }
//
//        if(hasFocus && text.value.isEmpty() && !searchTextList.value.isNullOrEmpty()){
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(text = "Search History")
//
//                Text(
//                    modifier = Modifier.clickable { weatherViewModel.clearSearchHistory() },
//                    text = "Clear All"
//                )
//            }
//
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            LazyRow {
//                searchTextList.value?.let { it ->
//                    items(it.size){
//                        Card(
//                            modifier = Modifier
//                                .padding(end = 8.dp)
//                                .clip(RoundedCornerShape(12.dp)),
//                            colors = CardDefaults.cardColors(
//                                containerColor = Color.LightGray
//                            )
//                        ) {
//                            Text(
//                                modifier = Modifier.padding(6.dp),
//                                text = searchTextList.value?.get(it)?.searchString.orEmpty()
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn(
//            modifier = Modifier.padding(vertical = 12.dp)
//        ) {
//            cittiesList.value?.let {
//                items(it.size){
//                    Text(
//                        modifier = Modifier.clickable {
//                            navHostController.navigate("detailScreen/${cittiesList.value!!.get(it).key}")
//                            weatherViewModel.clearCitiesList()
//                            weatherViewModel.insertSearchedText("${cittiesList.value!!.get(it).englishName.orEmpty()} , ${cittiesList.value!!.get(it).country?.englishName.orEmpty()}")
//                        },
//                        text = "${cittiesList.value!!.get(it).englishName.orEmpty()} , ${cittiesList.value!!.get(it).administrativeArea?.englishName.orEmpty()} , ${cittiesList.value!!.get(it).country?.englishName.orEmpty()}"
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }
//    }
//}

@Composable
fun EnhancedWeatherHomeScreen(
    navHostController: NavHostController,
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val citiesList = weatherViewModel.citiesList.observeAsState()
    val searchTextList = weatherViewModel.searchedTexts.observeAsState()
    val searchQuery = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var hasFocus by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        weatherViewModel.getSearchedTexts()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Cyan, Color.Blue)
                )
            )
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // App Header
            Text(
                text = "Weather Forecast",
//                style = MaterialTheme.typography.h4.copy(
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Search Bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                shape = RoundedCornerShape(12.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        value = searchQuery.value,
                        onValueChange = {
                            searchQuery.value = it
                            weatherViewModel.getCities(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { hasFocus = it.hasFocus },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        decorationBox = { innerTextField ->
                            if (searchQuery.value.isEmpty()) {
                                Text(
                                    text = "Search city...",
                                    style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            if (hasFocus && searchQuery.value.isEmpty() && !searchTextList.value.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                // Search History
                Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        modifier = Modifier.clickable {  weatherViewModel.clearSearchHistory()  },
                        text = "Clear all",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                LazyRow {
                    searchTextList.value?.let {
                        items(it.size) { index ->
                            Card(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .clickable {
                                        searchQuery.value = it[index].searchString.orEmpty()
                                        weatherViewModel.getCities(searchQuery.value)
                                    },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = it[index].searchString.orEmpty(),
                                    modifier = Modifier.padding(8.dp),
                                    style = TextStyle(color = Color.Black)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // City Results
            Text(
                text = "Search Results",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                citiesList.value?.let { list ->
                    items(list.size) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate("detailScreen/${list[index].key}")
                                    weatherViewModel.clearCitiesList()
                                    weatherViewModel.insertSearchedText(
                                        "${list[index].englishName.orEmpty()}, ${list[index].country?.englishName.orEmpty()}"
                                    )
                                },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = "Location Icon",
                                    tint = Color.Blue
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = list[index].englishName.orEmpty(),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = "${list[index].administrativeArea?.englishName.orEmpty()}, ${list[index].country?.englishName.orEmpty()}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DetailScreen(
    cityKey : String,
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val weather = weatherViewModel.cityWeather.observeAsState()

    LaunchedEffect(key1 = Unit){
        weatherViewModel.getWeather(cityKey)
    }

    LazyColumn {
        weather.value.let {
            it?.DailyForecasts?.let { it1 ->
                items(it1.size) {index->
                    Column {
                        Text(
                            text = weather.value?.DailyForecasts?.get(index)?.Date.orEmpty()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Day : ${weather.value?.DailyForecasts?.get(index)?.Day?.IconPhrase.orEmpty()}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Night : ${weather.value?.DailyForecasts?.get(index)?.Night?.IconPhrase.orEmpty()}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EnhancedDetailScreen(
    cityKey: String,
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val weather = weatherViewModel.cityWeather.observeAsState()
    val showLoader = weatherViewModel.showLoader.observeAsState()

    LaunchedEffect(key1 = Unit) {
        weatherViewModel.getWeather(cityKey)
    }

    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Cyan, Color.Blue)
                    )
                )
        ) {
            // Header
            Text(
                text = "Weather Forecast",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
            )

            // Forecast List
            weather.value?.DailyForecasts?.let { dailyForecasts ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(dailyForecasts.size) {forecast  ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            WeatherCard(dailyForecasts[forecast])
                        }

                    }
                }
            } ?: run {
                // Loading State
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(color = Color.White)
//            }
            }
        }

        if (showLoader.value == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false, onClick = {}), // Disable clicks behind the loader
                contentAlignment = Alignment.Center
            ) {
//                CircularProgressIndicator(
//                    color = Color.White,
//                    strokeWidth = 3.dp,
//                    modifier = Modifier.size(48.dp)
//                )
//                RotatingLoader()
                DotsLoadingAnimation()
            }
        }
    }


}

@Composable
fun RotatingLoader() {
    val infiniteTransition = rememberInfiniteTransition()

    // Rotate from 0 to 360 degrees
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing), // 1 second animation duration
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .size(50.dp) // Size of the loader
            .graphicsLayer(rotationZ = rotation) // Apply rotation
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    )
}

@Composable
fun CustomProgressLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(enabled = false, onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                DotsLoadingAnimation()
            }
        }
    }
}

@Composable
fun DotsLoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    // Create 3 dots with different phases for a wave effect
    val dots = List(3) { index ->
        val phase = index * 120f // 360/3 degrees phase difference
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(phase.toInt())
            )
        )
        scale
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        dots.forEachIndexed { index, scale ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .background(
                        color = Color.Blue,
                        shape = CircleShape
                    )
            )
        }
    }
}



@Composable
fun WeatherCard(forecast: DailyForecasts) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Date
            Text(
                text = formatDate(forecast.Date),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Day Details
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_sunny), // Replace with appropriate icons
                    contentDescription = "Day Icon",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Day: ${forecast.Day?.IconPhrase.orEmpty()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Night Details
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_moon), // Replace with appropriate icons
                    contentDescription = "Night Icon",
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Night: ${forecast.Night?.IconPhrase.orEmpty()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// Utility to format date
fun formatDate(date: String?): String {
    return date?.let {
        try {
            val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(it)
            SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(parsedDate)
        } catch (e: Exception) {
            it
        }
    } ?: "Unknown Date"
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        //Greeting("Android")
    }
}