package com.example.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Favorite

        if (departureCode != other.departureCode) return false
        if (destinationCode != other.destinationCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = departureCode.hashCode()
        result = 31 * result + destinationCode.hashCode()
        return result
    }
}
