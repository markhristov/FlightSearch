package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightsDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDAO
    abstract fun favoriteDao(): FavoriteDAO

    companion object {
        @Volatile
        private var Instance: FlightsDatabase? = null

        fun getDatabase(context: Context): FlightsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightsDatabase::class.java, "flight_database")
                    .fallbackToDestructiveMigration(false)
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}