package com.nikhilchauhan.locationtracker.repository

import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
  @ApplicationContext private val context: Context,
  private var locationCallback: LocationCallback
) : LocationRepository {
  private var fusedLocationClient: FusedLocationProviderClient =
    LocationServices.getFusedLocationProviderClient(context)
  private lateinit var locationRequest: LocationRequest

  init {
    setLocationRequest()
    createFile()
    Log.d("ReadData", ": " + readDataFromFile())
  }

  private fun createFile() {
    File(context.applicationContext.filesDir, "locations.txt").createNewFile()
  }

  private fun setLocationRequest() {
    locationRequest = LocationRequest.create().apply {
      interval = 100
      fastestInterval = 100
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
      smallestDisplacement = 0F
    }
  }

  override fun startLocationUpdates() {
    fusedLocationClient.requestLocationUpdates(
      locationRequest,
      locationCallback,
      Looper.getMainLooper()
    )
  }

  override fun stopLocationUpdates() {
    fusedLocationClient.removeLocationUpdates(locationCallback)
  }

  override fun writeDataToFile(
    latitude: Double,
    longitude: Double
  ) {
    context.applicationContext.openFileOutput("locations.txt", Context.MODE_APPEND)
      .bufferedWriter()
      .use { fos ->
        fos.append("Latitude : $latitude \t Longitude : $longitude\n")
      }
  }

  override fun readDataFromFile(): String {
    return context.applicationContext.openFileInput("locations.txt").bufferedReader()
      .use { it.readText() }
  }
}