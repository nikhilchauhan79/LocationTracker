package com.nikhilchauhan.locationtracker.loginscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nikhilchauhan.locationtracker.datastore.PrefDataStoreManager
import com.nikhilchauhan.locationtracker.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val prefDataStoreManager: PrefDataStoreManager) : ViewModel() {
  val name = mutableStateOf("")
  val mobileNumber = mutableStateOf("")

  suspend fun login() {
    prefDataStoreManager.saveToDataStore(User(name.value, mobileNumber.value))
  }
}