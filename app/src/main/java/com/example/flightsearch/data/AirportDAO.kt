package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDAO {

    @Query(value = "SELECT * FROM airport WHERE iata_code LIKE '%' || :code || '%'")
    fun getAirportWithCode(code: String): Flow<List<Airport>>

    @Query(value = "SELECT * FROM airport WHERE name LIKE '%' || :name || '%'")
    fun getAirportWithName(name: String): Flow<List<Airport>>

    @Query(value = "SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>
}
