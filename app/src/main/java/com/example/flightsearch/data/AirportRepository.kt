package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAirportWithCode(code: String): Flow<List<Airport>>

    fun getAirportWithName(name: String): Flow<List<Airport>>

    fun getAllAirports(): Flow<List<Airport>>
}
