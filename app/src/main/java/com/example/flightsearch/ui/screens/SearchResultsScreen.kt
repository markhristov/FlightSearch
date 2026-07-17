package com.example.flightsearch.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun SearchResultsScreen(
    airports: List<Airport>,
    onCardClick: (Airport) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(airports) { airport ->
            AirportCard(
                airport,
                onCardClick = onCardClick,
                modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AirportCard(airport: Airport, onCardClick: (Airport) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.clickable(onClick = {onCardClick(airport)}),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = airport.iataCode,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.size(16.dp))
        Text(
            text = airport.name
        )
    }
}

@Preview(showBackground = true,  backgroundColor = 0xFFFFFFFF
)
@Composable
fun SearchResultsScreenPreview() {
    FlightSearchTheme {
        val airports = listOf(
            Airport(
                id = 1,
                iataCode = "JFK",
                name = "John F. Kennedy International Airport",
                passengers = 61_000_000
            ),
            Airport(
                id = 2,
                iataCode = "LAX",
                name = "Los Angeles International Airport",
                passengers = 75_000_000
            ),
            Airport(
                id = 3,
                iataCode = "LHR",
                name = "London Heathrow Airport",
                passengers = 79_000_000
            ),
            Airport(
                id = 4,
                iataCode = "CDG",
                name = "Charles de Gaulle Airport",
                passengers = 67_000_000
            ))
        SearchResultsScreen(airports, {})
    }
}