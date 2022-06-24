package com.nikhilchauhan.locationtracker.locationworker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoroutineLocationWorker(
  context: Context,
  params: WorkerParameters
) : CoroutineWorker(context, params) {
  override suspend fun doWork(): Result {
    withContext(Dispatchers.IO){

    }
    return Result.success()
  }
}