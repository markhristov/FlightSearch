package com.example.flightsearch.ui.flight

import com.example.flightsearch.data.Airport

data class Flight(
    val origin: Airport,
    val destination: Airport,
    val starred: Boolean = false
)

