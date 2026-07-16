package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun insert(favorite: Favorite)

    suspend fun update(favorite: Favorite)

    suspend fun delete(favorite: Favorite)

    fun getFavorites(): Flow<List<Favorite>>
}
