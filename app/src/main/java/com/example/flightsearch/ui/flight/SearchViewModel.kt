package com.example.flightsearch.ui.flight

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportRepository
import com.example.flightsearch.data.FavoritesRepository
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class SearchViewModel(
    private val airportRepository: AirportRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    var flightsUiState by mutableStateOf(FlightSearchUiState())
        private set

    fun changeQuery(newQuery: String) {
        flightsUiState = flightsUiState.copy(
            query = newQuery
        )
    }

    suspend fun searchQuery(query: String): List<Airport> {
        val airportsByCode = airportRepository.getAirportWithCode(query)
        val airportsByName = airportRepository.getAirportWithName(query)

        return airportsByCode + airportsByName
    }

    fun queryBlank() : Boolean {
        return flightsUiState.query.isBlank()
    }
}


data class FlightSearchUiState(
    val query: String = "",
    val airports: List<Airport> = emptyList(),
    val favorites: List<FavoriteFlights> = emptyList()
)