package com.example.flightsearch.ui.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightsApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportRepository
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel(
    private val airportRepository: AirportRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private var _flightsUiState = MutableStateFlow(FlightSearchUiState())
    val flightsUiState = _flightsUiState.asStateFlow()
    val favoriteFlights: StateFlow<List<Flight>> =
        combine(
            favoritesRepository.getAllFavorites(),
            airportRepository.getAllAirports()
        ) { favorites, airports ->

            val airportMap = airports.associateBy { it.iataCode }

            favorites.mapNotNull { favorite ->
                val origin = airportMap[favorite.departureCode]
                val destination = airportMap[favorite.destinationCode]

                if (origin != null && destination != null) {
                    Flight(
                        origin = origin,
                        destination = destination
                    )
                } else {
                    null
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun updateQuery(query: String) {
        _flightsUiState.update {
            it.copy(query = query)
        }
    }

    fun search(query: String) {
        updateQuery(query)
        viewModelScope.launch {
            combine(
                airportRepository.getAirportWithCode(query),
                airportRepository.getAirportWithName(query)
            ) { byCode, byName ->
                (byCode + byName).distinctBy { it.id }
            }.collect { airports ->
                _flightsUiState.update {
                    it.copy(
                        content = SearchContent.AirportResults(airports)
                    )
                }
            }
        }
    }

    fun getFlightsWithOrigin(origin: Airport) {
        viewModelScope.launch {
            airportRepository.getAllAirports()
                .map { airports ->
                    airports
                        .filter { it.id != origin.id }
                }
                .collect { destinations ->
                    _flightsUiState.update {
                        it.copy(
                            content = SearchContent.FlightResults(
                                origin = origin,
                                destinations = destinations
                            )
                        )
                    }
                }
        }
    }

    fun addToFavorites(flight: Flight) {
        viewModelScope.launch {
            favoritesRepository.insert( Favorite(departureCode = flight.origin.iataCode, destinationCode = flight.destination.iataCode))
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

sealed interface SearchContent {
    data object Favorites: SearchContent

    data class AirportResults(
        val airports: List<Airport>
    ) : SearchContent

    data class FlightResults(
        val origin: Airport,
        val destinations: List<Airport>
    ) : SearchContent
}

data class FlightSearchUiState(
    val query: String = "",
    val content: SearchContent = SearchContent.Favorites
)