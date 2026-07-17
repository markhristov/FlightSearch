package com.example.flightsearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.flight.FlightSearchUiState
import com.example.flightsearch.ui.flight.SearchContent
import com.example.flightsearch.ui.flight.SearchViewModel
import com.example.flightsearch.ui.screens.AirportResultsScreen
import com.example.flightsearch.ui.screens.FavoritesScreen
import com.example.flightsearch.ui.screens.FlightResultsScreen
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FlightsApp(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {
    val uiState by viewModel.flightsUiState.collectAsState()
    Scaffold(
        topBar = { InventoryTopAppBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            FlightsSearchBar(
                uiState = uiState,
                onSearch = {
                    viewModel.search(it)
                },
            )
            val favorites by viewModel.favoriteFlights.collectAsState()
            when (val content = uiState.content){
                is SearchContent.Favorites ->
                    FavoritesScreen(favorites ,{viewModel.removeFromFavorites(it)})
                is SearchContent.AirportResults ->
                    AirportResultsScreen(content.airports, {viewModel.selectAirport(it)})
                is SearchContent.FlightResults ->
                    FlightResultsScreen(content.origin, content.destinations, {viewModel.addToFavorites(it)})
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsSearchBar(
    uiState: FlightSearchUiState, onSearch: (String) -> Unit, modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxWidth()

    ) {
        OutlinedTextField(
            value = uiState.query,
            onValueChange = onSearch,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.search_airports)) },
            singleLine = true,
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FlightsSearchBarEmptyPreview() {
    FlightSearchTheme {
        FlightsSearchBar(
            uiState = FlightSearchUiState(), onSearch = {})
    }
}