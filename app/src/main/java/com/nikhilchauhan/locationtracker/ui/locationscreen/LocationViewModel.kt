package com.nikhilchauhan.locationtracker.ui.locationscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nikhilchauhan.locationtracker.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(): ViewModel() {
  val locations = mutableStateOf(listOf<Location>())

  fun getSavedLocation(){

  }
}