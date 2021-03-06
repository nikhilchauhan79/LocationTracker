package com.nikhilchauhan.locationtracker.ui.locationscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.ExistingWorkPolicy.REPLACE
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.nikhilchauhan.locationtracker.locationworker.CoroutineLocationWorker
import com.nikhilchauhan.locationtracker.model.Location

@Composable
fun LocationTrackingScreen(locationViewModel: LocationViewModel = hiltViewModel()) {
  val context = LocalContext.current

  LaunchedEffect(key1 = true) {
    val locationWorkRequest = OneTimeWorkRequest.from(CoroutineLocationWorker::class.java)
    WorkManager.getInstance(context).enqueueUniqueWork("workLocation", REPLACE, locationWorkRequest)
  }

  LazyColumn(
    contentPadding = PaddingValues(8.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(locationViewModel.locations) { location ->
      LocationItem(location)
    }
  }
}

@Composable fun LocationItem(location: Location) {
  val decimalSyntax = "%.4f"
  Card(
    shape = RoundedCornerShape(8.dp),
    elevation = 8.dp,
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth(0.5F)
      ) {
        LocationParamRow("Latitude: ", decimalSyntax.format(location.latitude))
        LocationParamRow("Longitude: ", decimalSyntax.format(location.longitude))
      }

      Column(modifier = Modifier.fillMaxWidth(1F)) {
        LocationParamRow("Altitude: ", decimalSyntax.format(location.altitude))
        LocationParamRow("Bearing: ", decimalSyntax.format(location.bearing))
      }
    }
  }
}

@Composable
private fun LocationParamRow(
  head: String,
  value: String
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      text = head, style = TextStyle(fontSize = 14.sp), modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(text = value, style = TextStyle(fontSize = 14.sp))
  }
}