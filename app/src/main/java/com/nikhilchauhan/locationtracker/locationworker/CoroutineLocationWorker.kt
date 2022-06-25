package com.nikhilchauhan.locationtracker.locationworker

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.nikhilchauhan.locationtracker.model.Location
import com.nikhilchauhan.locationtracker.repository.LocationRepository
import com.nikhilchauhan.locationtracker.repository.LocationRepositoryImpl
import com.nikhilchauhan.locationtracker.utils.FileUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@HiltWorker
class CoroutineLocationWorker @AssistedInject constructor(
  @Assisted private val context: Context,
  @Assisted params: WorkerParameters,
  private val fileUtils: FileUtils
) : CoroutineWorker(context, params) {
  lateinit var locationCallback: LocationCallback
  lateinit var locationRepository: LocationRepository
  private var notificationUtils: NotificationUtils
  var job: Job? = null

  init {
    setLocationCallback()
    setLocationRepository()
    notificationUtils = NotificationUtils(context, id)
  }

  private fun setLocationRepository() {
    locationRepository = LocationRepositoryImpl(context, locationCallback)
  }

  @OptIn(ExperimentalTime::class)
  override suspend fun doWork(): Result {
    return try {
      setForeground(getForegroundInfo())
      coroutineScope {
        job = async {
          locationRepository.startLocationUpdates()
          delay(Duration.INFINITE)
        }
        async {
          while (true) {
            if (isStopped) {
              hideNotificationAndStopLocationUpdates()
              job?.cancel()
              return@async Result.success()
            }
          }
        }
      }
      Result.success()
    } catch (exception: Exception) {
      Result.success()
    }
  }

  private fun hideNotificationAndStopLocationUpdates() {
    locationRepository.stopLocationUpdates()
    val notificationManager =
      applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancelAll()
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
            fileUtils.writeDataToFile(Location(latitude, longitude, altitude, bearing))
          }
        }
      }
    }
  }

  override suspend fun getForegroundInfo(): ForegroundInfo {
    return notificationUtils.createForegroundInfo()
  }
}