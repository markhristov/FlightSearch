package com.example.flightsearch.ui.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightsApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportRepository
import com.example.flightsearch.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel(
    private val airportRepository: AirportRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private var _flightsUiState = MutableStateFlow(FlightSearchUiState())
    val flightsUiState = _flightsUiState.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            combine(
                airportRepository.getAirportWithCode(query),
                airportRepository.getAirportWithName(query)
            ) { byCode, byName ->
                (byCode + byName).distinctBy { it.id }
            }.collect { airports ->
                _flightsUiState.update {
                    it.copy(
                        query = query,
                        airports = airports
                    )
                }
            }
        }
    }

    companion object{
        val Factory = viewModelFactory {

            initializer {
                val application = this[APPLICATION_KEY] as FlightsApplication
                SearchViewModel(
                    application.container.airportRepository,
                    application.container.favoriteRepository
                )
            }
        }
    }

}


data class FlightSearchUiState(
    val query: String = "",
    val airports: List<Airport> = emptyList(),
    val favorites: List<FavoriteFlights> = emptyList()
)