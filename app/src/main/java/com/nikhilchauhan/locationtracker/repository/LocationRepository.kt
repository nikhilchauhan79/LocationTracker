package com.nikhilchauhan.locationtracker.repository

interface LocationRepository {
  fun startLocationUpdates()
  fun stopLocationUpdates()
  fun writeDataToFile( latitude: Double,
    longitude: Double)
  fun readDataFromFile(): String
}