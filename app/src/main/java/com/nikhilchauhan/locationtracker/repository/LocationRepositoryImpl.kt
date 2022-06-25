package com.nikhilchauhan.locationtracker.repository

import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
  @ApplicationContext context: Context,
  private var locationCallback: LocationCallback
) : LocationRepository {
  private var fusedLocationClient: FusedLocationProviderClient
  lateinit var locationRequest: LocationRequest

  init {
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    setLocationRequest()
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
}