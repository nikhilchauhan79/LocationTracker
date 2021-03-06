package com.nikhilchauhan.locationtracker.ui.loginscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nikhilchauhan.locationtracker.datastore.PrefDataStoreManager
import com.nikhilchauhan.locationtracker.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val prefDataStoreManager: PrefDataStoreManager) :
  ViewModel() {
  val name = mutableStateOf("")
  val mobileNumber = mutableStateOf("")
  val loginState = mutableStateOf(false)

  suspend fun login() {
    if (name.value.trim().isNotBlank() && mobileNumber.value.trim().isNotBlank()) {
      prefDataStoreManager.saveToDataStore(User(name.value, mobileNumber.value))
      loginState.value = true
    }
  }
}