package com.nikhilchauhan.locationtracker.ui.locationscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilchauhan.locationtracker.model.LocationResponse
import com.nikhilchauhan.locationtracker.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
  private val fileUtils: FileUtils
) : ViewModel() {
  val locations = mutableStateOf(LocationResponse())

  init {
    getSavedLocations()
  }

  private fun getSavedLocations() {
    viewModelScope.launch {
      locations.value = fileUtils.readDataFromFile()
    }
  }
}