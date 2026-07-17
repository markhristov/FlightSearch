package com.example.flightsearch.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.flight.Flight
import com.example.flightsearch.ui.theme.FlightSearchTheme


@Composable
fun FlightsScreen(flights: List<Flight>, onStarClick: (Flight) -> Unit,
                   modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(flights) { flight ->
            FavoriteCard(
                flight = flight,
                onStarClick = onStarClick,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FavoritesScreen(flights: List<Flight>, onStarClick: (Flight) -> Unit,
                    modifier: Modifier = Modifier) {
    FlightsScreen(flights, onStarClick, modifier)
}


@Composable
fun FlightResultsScreen(
    origin: Airport,
    destinations: List<Airport>,
    favorites: List<Flight>,
    onStarClick: (Flight) -> Unit,
    modifier: Modifier = Modifier,
) {
    val favoriteRoutes = favorites.map { favorite ->
        favorite.origin.iataCode to favorite.destination.iataCode
    }.toSet()
    val flights = destinations.map { destination ->
        Flight(
            origin = origin,
            destination = destination,
            starred = origin.iataCode to destination.iataCode in favoriteRoutes
        )
    }
    FlightsScreen(flights, onStarClick, modifier)
}

@Composable
fun FavoriteCard(
    flight: Flight,
    modifier: Modifier = Modifier,
    onStarClick: (Flight) -> Unit
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)) {
                FlightInfo(
                    airport = flight.origin,
                    true,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                FlightInfo(
                    airport = flight.destination,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

            }
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
                    .clickable(onClick = {onStarClick(flight)}),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (flight.starred) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = if (flight.starred) "Remove from favorites" else "Add to favorites" ,
                    modifier = Modifier.size(32.dp),
                    tint =  if (flight.starred) Color(0xFFFFA000) else LocalContentColor.current
                )
            }
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
            Flight(
                origin = Airport(
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
            Flight(
                origin = Airport(
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
            Flight(
                origin = Airport(
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
        FlightsScreen(favoriteItems, {})
    }
}
