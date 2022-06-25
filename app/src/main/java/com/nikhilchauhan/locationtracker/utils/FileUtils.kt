package com.nikhilchauhan.locationtracker.utils

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.nikhilchauhan.locationtracker.model.Location
import com.nikhilchauhan.locationtracker.model.LocationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

class FileUtils @Inject constructor(@ApplicationContext private val context: Context) {
  init {
    createFile()
  }

  private fun createFile() {
    File(context.applicationContext.filesDir, "locations.txt").also {
      if (!(it.exists()))
        it.createNewFile()
    }
  }

  fun writeDataToFile(
   location: Location
  ) {
    context.applicationContext.openFileOutput("locations.txt", Context.MODE_APPEND)
      .bufferedWriter()
      .use { fos ->
        val jsonObj = JSONObject()
        jsonObj.put("latitude", location.latitude.toString())
        jsonObj.put("longitude", location.longitude.toString())
        jsonObj.put("altitude", location.altitude.toString())
        jsonObj.put("bearing", location.bearing.toString())

        fos.append("$jsonObj,")
      }
  }

  fun readDataFromFile(): LocationResponse {
    context.applicationContext.openFileInput("locations.txt").bufferedReader()
      .use {
        val gson = GsonBuilder().setLenient().create()
        val originalText = it.readText()
        Log.d("TAG", "readDataFromFile: $originalText")

        Log.d("Json", "{\"locations\":[${originalText.dropLast(1)}]}")
        return gson.fromJson(
          "{\"locations\":[${originalText.dropLast(1)}]}", LocationResponse::class.java
        )
      }
  }
}