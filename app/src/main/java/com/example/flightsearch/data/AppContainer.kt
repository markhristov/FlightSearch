/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.flightsearch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.local.UserPreferencesRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoritesRepository
    val userPreferencesRepository: UserPreferencesRepository
}

private const val QUERY_NAME = "current_query"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = QUERY_NAME
)
class AppDataContainer(private val context: Context,

) : AppContainer {

    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(FlightsDatabase.getDatabase(context).airportDao())
    }

    override val favoriteRepository: FavoritesRepository by lazy {
        OfflineFavoritesRepository(FlightsDatabase.getDatabase(context).favoriteDao())
    }

    override val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepository(context.dataStore)
}
