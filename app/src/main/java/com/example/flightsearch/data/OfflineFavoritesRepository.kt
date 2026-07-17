package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class OfflineFavoritesRepository(
    private val favoriteDAO: FavoriteDAO
) : FavoritesRepository {
    override suspend fun insert(favorite: Favorite) =
        favoriteDAO.insert(favorite)

    override suspend fun update(favorite: Favorite) =
        favoriteDAO.update(favorite)

    override suspend fun delete(favorite: Favorite) =
        favoriteDAO.delete(favorite)

    override fun getAllFavorites(): Flow<List<Favorite>> =
        favoriteDAO.getFavorites()
}
