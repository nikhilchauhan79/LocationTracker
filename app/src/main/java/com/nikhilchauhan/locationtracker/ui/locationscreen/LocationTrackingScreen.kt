package com.nikhilchauhan.locationtracker.ui.locationscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikhilchauhan.locationtracker.model.Location

@Composable
fun LocationTrackingScreen(locationViewModel: LocationViewModel = hiltViewModel()) {
  LazyColumn(
    contentPadding = PaddingValues(8.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(locationViewModel.locations.value) { location ->
      LocationItem(location)
    }
  }
}

@Composable fun LocationItem(location: Location) {
  Card(
    shape = RoundedCornerShape(8.dp),
    elevation = 8.dp,
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .padding(top = 4.dp, bottom = 12.dp)
  ) {
    Text(text = location.latitude.toString())
    Text(text = location.longitude.toString())
  }
}