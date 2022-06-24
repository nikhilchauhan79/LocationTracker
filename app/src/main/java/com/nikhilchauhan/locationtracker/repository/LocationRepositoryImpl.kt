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

class LocationRepositoryImpl @Inject constructor(@ApplicationContext context: Context) : LocationRepository {
  var fusedLocationClient: FusedLocationProviderClient
  lateinit var locationRequest: LocationRequest
  lateinit var locationCallback: LocationCallback

  init {
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    setLocationCallback()
    setLocationRequest()
  }

  private fun setLocationRequest() {
    locationRequest = LocationRequest.create().apply {
      interval = 100
      fastestInterval = 100
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
      smallestDisplacement = 1F
    }
  }

  override fun startLocationUpdates() {
    fusedLocationClient.requestLocationUpdates(
      locationRequest,
      locationCallback,
      Looper.getMainLooper()
    )
  }

  private fun setLocationCallback() {
    locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult) {
        super.onLocationResult(locationResult)
        locationResult.locations.forEach { location ->
          location?.run {
            Log.d(
              LocationRepositoryImpl::class.java.simpleName,
              "Latitude $latitude Longitude $longitude"
            )
          }
        }
      }
    }
  }
}