package com.example.flightsearch

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearch.ui.home.FavoritesScreen
import com.example.flightsearch.ui.home.SearchResultsScreen

@Composable
fun FlightsApp(

) {
    val searchBarEmpty = true
    Scaffold(
        topBar = { InventoryTopAppBar() }) { paddingValues ->
        if (searchBarEmpty) {
            FavoritesScreen(listOf(), paddingValues = paddingValues)
        } else {
            SearchResultsScreen(listOf(), paddingValues = paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Flight Search",
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
    )
}
