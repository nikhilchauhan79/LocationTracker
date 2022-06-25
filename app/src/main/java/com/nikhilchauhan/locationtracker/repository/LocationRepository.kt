package com.nikhilchauhan.locationtracker.repository

interface LocationRepository {
  fun startLocationUpdates()
  fun stopLocationUpdates()
}