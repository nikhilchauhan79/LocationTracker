package com.nikhilchauhan.locationtracker.injection

import android.content.Context
import com.nikhilchauhan.locationtracker.datastore.PrefDataStoreManager
import com.nikhilchauhan.locationtracker.repository.LocationRepository
import com.nikhilchauhan.locationtracker.repository.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun providePrefDataStoreManager(@ApplicationContext context: Context): PrefDataStoreManager =
    PrefDataStoreManager(context)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

  @Binds
  @Singleton
  abstract fun bindsLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}