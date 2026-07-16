package com.example.flightsearch.ui.home

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.flight.FavoriteFlights
import com.example.flightsearch.ui.theme.FlightSearchTheme


@Composable
fun FavoritesScreen(
    favoriteItems: List<FavoriteFlights>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    LazyColumn(modifier = modifier.padding(paddingValues)) {
        items(favoriteItems) { favorite ->
            FavoriteCard(favorite,
                modifier.fillMaxWidth())
        }
    }
}

@Composable
fun FavoriteCard(favoriteFlight: FavoriteFlights, modifier: Modifier = Modifier) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f).padding(16.dp)) {
            FlightInfo(airport = favoriteFlight.source, true, modifier = Modifier.padding(vertical = 8.dp))

            FlightInfo(airport = favoriteFlight.destination, modifier = Modifier.padding(vertical = 8.dp))

        }
        Box(modifier = Modifier.size(64.dp).padding(end=16.dp),
            contentAlignment = Alignment.Center
) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "star",
                modifier = Modifier.size(32.dp),
                tint = Color(0xFFFFA000)
            )
        }
    }
}

@Composable
fun FlightInfo(airport: Airport, source: Boolean = false, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = if (source) "DEPART" else "ARRIVE")
        Row() {
            Text(text = airport.iataCode, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = airport.name)
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun FlightsScreenPreview() {
    FlightSearchTheme() {
        val favoriteItems = listOf(
            FavoriteFlights(
                source = Airport(
                    id = 1,
                    iataCode = "JFK",
                    name = "John F. Kennedy International Airport",
                    passengers = 61_000_000
                ),
                destination = Airport(
                    id = 2,
                    iataCode = "LAX",
                    name = "Los Angeles International Airport",
                    passengers = 75_000_000
                )
            ),
            FavoriteFlights(
                source = Airport(
                    id = 3,
                    iataCode = "LHR",
                    name = "London Heathrow Airport",
                    passengers = 79_000_000
                ),
                destination = Airport(
                    id = 4,
                    iataCode = "CDG",
                    name = "Charles de Gaulle Airport",
                    passengers = 67_000_000
                )
            ),
            FavoriteFlights(
                source = Airport(
                    id = 5,
                    iataCode = "NRT",
                    name = "Narita International Airport",
                    passengers = 40_000_000
                ),
                destination = Airport(
                    id = 6,
                    iataCode = "SYD",
                    name = "Sydney Airport",
                    passengers = 44_000_000
                )
            )
        )
        FavoritesScreen(favoriteItems)
    }
}