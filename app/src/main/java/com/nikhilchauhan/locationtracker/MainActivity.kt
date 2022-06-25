package com.nikhilchauhan.locationtracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.nikhilchauhan.locationtracker.ui.loginscreen.LoginScreen
import com.nikhilchauhan.locationtracker.repository.LocationRepository
import com.nikhilchauhan.locationtracker.ui.theme.LocationTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      LocationTrackerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          Scaffold(
            topBar = {
              TopAppBar(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Location Tracker App")
              }
            }
          ) {
            LoginScreen()
          }
        }
      }
    }
    checkLocationPermission()
  }

  private fun checkLocationPermission() {
    when {
      ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED -> {
        Log.d(MainActivity::class.java.simpleName, "Location permission granted")
        // locationRepository.startLocationUpdates()
      }

      else -> {
        requestPermissions(
          this,
          arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
          ),
          AppContants.LOCATION_PERMISSION_REQUEST_CODE
        )
      }
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
      AppContants.LOCATION_PERMISSION_REQUEST_CODE
      -> {
        if ((grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED)
        ) {
        } else {
          Log.d(MainActivity::class.java.simpleName, "permission not granted")
          Toast.makeText(this, "permission not granted", Toast.LENGTH_LONG).show()
        }
        return
      }
      else -> {
      }
    }
  }
}
