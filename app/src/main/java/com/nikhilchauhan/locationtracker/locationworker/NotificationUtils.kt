package com.nikhilchauhan.locationtracker.locationworker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import com.nikhilchauhan.locationtracker.AppContants.WORKER_ID
import com.nikhilchauhan.locationtracker.R
import java.util.UUID

class NotificationUtils(
  private val context: Context,
  private val id: UUID
) {
  fun createForegroundInfo(): ForegroundInfo {
    val intent = WorkManager.getInstance(context.applicationContext)
      .createCancelPendingIntent(id)

    val notification = NotificationCompat.Builder(
      context.applicationContext, "workLocation"
    )
      .setContentTitle("Tracking Location")
      .setTicker("Tracking Location")
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setOngoing(true)
      .addAction(android.R.drawable.ic_delete, "Stop Location Updates", intent)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      createChannel(notification, WORKER_ID)
    }
    return ForegroundInfo(1, notification.build())
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun createChannel(
    notificationBuilder: NotificationCompat.Builder,
    id: String
  ) {
    val notificationManager =
      context.getSystemService(Context.NOTIFICATION_SERVICE) as
        NotificationManager
    notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
    val channel = NotificationChannel(
      id,
      "LocationTrackerApp",
      NotificationManager.IMPORTANCE_HIGH
    )
    channel.description = "LocationTrackerApp Notifications"
    notificationManager.createNotificationChannel(channel)
  }
}