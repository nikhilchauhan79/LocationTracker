package com.nikhilchauhan.locationtracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilchauhan.locationtracker.datastore.PrefDataStoreManager
import com.nikhilchauhan.locationtracker.model.User
import com.nikhilchauhan.locationtracker.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val prefDataStoreManager: PrefDataStoreManager
) : ViewModel() {
  private val isLoggedIn = mutableStateOf(false)
  val startDest = mutableStateOf("")

  init {
    viewModelScope.launch {
      isLoggedIn.value = isUserLoggedIn()
      computeStartDestination()
    }
  }

  private fun computeStartDestination() {
    startDest.value = if (isLoggedIn.value) {
      NavRoutes.Location.route
    } else NavRoutes.Login.route
  }

  private suspend fun isUserLoggedIn(): Boolean =
    getUser().run {
      this != null && name.isNotBlank() && mobileNumber.isNotBlank()
    }

  suspend fun getUser(): User? = prefDataStoreManager.getUserFromDataStore()
}