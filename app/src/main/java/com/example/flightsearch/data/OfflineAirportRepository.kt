package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(
    private val airportDAO: AirportDAO
) : AirportRepository {
    override fun getAirportWithCode(code: String): Flow<List<Airport>> =
        airportDAO.getAirportWithCode(code)

    override fun getAirportWithName(name: String): Flow<List<Airport>> =
        airportDAO.getAirportWithName(name)

    override fun getAllAirports(): Flow<List<Airport>> =
        airportDAO.getAllAirports()
}
