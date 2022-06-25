package com.nikhilchauhan.locationtracker.ui.locationscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilchauhan.locationtracker.model.Location
import com.nikhilchauhan.locationtracker.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
  private val fileUtils: FileUtils
) : ViewModel() {
  val locations = mutableStateListOf<Location>()

  init {
    getSavedLocations()
  }

  private fun getSavedLocations() {
    viewModelScope.launch {
      while (true) {
        locations.addAll(fileUtils.readDataFromFile().locations)
        delay(600000)
      }
    }
  }
}