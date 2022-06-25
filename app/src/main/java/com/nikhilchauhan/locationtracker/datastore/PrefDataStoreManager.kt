package com.nikhilchauhan.locationtracker.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nikhilchauhan.locationtracker.AppContants
import com.nikhilchauhan.locationtracker.AppContants.LOCATION_DATASTORE
import com.nikhilchauhan.locationtracker.model.User
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrefDataStoreManager @Inject constructor(private val context: Context) {
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LOCATION_DATASTORE
  )

  companion object {
    val NAME = stringPreferencesKey(AppContants.NAME)
    val MOBILE_NUMBER = stringPreferencesKey(AppContants.MOBILE_NUMBER)
  }

  suspend fun saveToDataStore(user: User) {
    context.dataStore.edit { pref ->
      pref[NAME] = user.name
      pref[MOBILE_NUMBER] = user.mobileNumber
    }
  }

  suspend fun getUserFromDataStore(): User? {
    return context.dataStore.data.map { pref ->
      User(pref[NAME].orEmpty(), pref[MOBILE_NUMBER].orEmpty()).also {
        Log.d("login", "getUserFromDataStore: "+it.toString())
      }
    }.firstOrNull()
  }
}